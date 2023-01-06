package com.livk.auth.server.config;

import com.livk.auth.server.common.constant.SecurityConstants;
import com.livk.auth.server.common.converter.OAuth2PasswordAuthenticationConverter;
import com.livk.auth.server.common.converter.OAuth2SmsAuthenticationConverter;
import com.livk.auth.server.common.core.FormIdentityLoginConfigurer;
import com.livk.auth.server.common.core.UserDetailsAuthenticationProvider;
import com.livk.auth.server.common.core.customizer.OAuth2JwtTokenCustomizer;
import com.livk.auth.server.common.handler.AuthenticationFailureEventHandler;
import com.livk.auth.server.common.handler.AuthenticationSuccessEventHandler;
import com.livk.auth.server.common.provider.OAuth2PasswordAuthenticationProvider;
import com.livk.auth.server.common.provider.OAuth2SmsAuthenticationProvider;
import com.livk.auth.server.common.util.JwkUtils;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.DelegatingOAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.JwtGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2RefreshTokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.web.authentication.DelegatingAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2AuthorizationCodeAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2ClientCredentialsAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2RefreshTokenAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.List;

/**
 * <p>
 * AuthorizationServerConfiguration
 * </p>
 *
 * @author livk
 */
@Configuration
public class AuthorizationServerConfiguration {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http,
                                                                      OAuth2AuthorizationService authorizationService,
                                                                      AuthenticationManagerBuilder authenticationManagerBuilder,
                                                                      OAuth2TokenGenerator<OAuth2Token> oAuth2TokenGenerator,
                                                                      UserDetailsAuthenticationProvider userDetailsAuthenticationProvider,
                                                                      AuthorizationServerSettings authorizationServerSettings) throws Exception {
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
        RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();

        http.securityMatcher(endpointsMatcher)
                .authorizeHttpRequests()
                .requestMatchers("/auth/**")
                .permitAll()
                .requestMatchers("/actuator/**")
                .permitAll()
                .requestMatchers("/css/**")
                .permitAll()
                .requestMatchers("/error")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
                .apply(authorizationServerConfigurer);

        AuthenticationManager authenticationManager = authenticationManagerBuilder.getObject();

        OAuth2PasswordAuthenticationProvider resourceOwnerPasswordAuthenticationProvider = new OAuth2PasswordAuthenticationProvider(
                authenticationManager, authorizationService, oAuth2TokenGenerator);

        OAuth2SmsAuthenticationProvider resourceOwnerSmsAuthenticationProvider = new OAuth2SmsAuthenticationProvider(
                authenticationManager, authorizationService, oAuth2TokenGenerator);

        OAuth2AuthorizationServerConfigurer configurer = authorizationServerConfigurer.tokenEndpoint(tokenEndpoint ->
                        tokenEndpoint.accessTokenRequestConverter(accessTokenRequestConverter())
                                .authenticationProvider(userDetailsAuthenticationProvider)
                                .authenticationProvider(resourceOwnerPasswordAuthenticationProvider)
                                .authenticationProvider(resourceOwnerSmsAuthenticationProvider)
                                .accessTokenResponseHandler(new AuthenticationSuccessEventHandler())
                                .errorResponseHandler(new AuthenticationFailureEventHandler()))
                .clientAuthentication(oAuth2ClientAuthenticationConfigurer ->
                        oAuth2ClientAuthenticationConfigurer
                                .errorResponseHandler(new AuthenticationFailureEventHandler()))
                .authorizationEndpoint(authorizationEndpoint ->
                        authorizationEndpoint.consentPage(SecurityConstants.CUSTOM_CONSENT_PAGE_URI))
                .authorizationService(authorizationService)
                .authorizationServerSettings(authorizationServerSettings)
                .oidc(Customizer.withDefaults());

        return http.apply(configurer)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
                .and()
                .apply(new FormIdentityLoginConfigurer())
                .and()
                .formLogin(Customizer.withDefaults())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        RSAKey rsaKey = JwkUtils.generateRsa();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }

    @Bean
    public OAuth2TokenGenerator<OAuth2Token> oAuth2TokenGenerator(JwtEncoder jwtEncoder) {
        JwtGenerator generator = new JwtGenerator(jwtEncoder);
        generator.setJwtCustomizer(new OAuth2JwtTokenCustomizer());
        return new DelegatingOAuth2TokenGenerator(generator, new OAuth2RefreshTokenGenerator());
    }

    private AuthenticationConverter accessTokenRequestConverter() {
        return new DelegatingAuthenticationConverter(List.of(
                new OAuth2AuthorizationCodeAuthenticationConverter(),
                new OAuth2ClientCredentialsAuthenticationConverter(),
                new OAuth2RefreshTokenAuthenticationConverter(),
                new OAuth2PasswordAuthenticationConverter(),
                new OAuth2SmsAuthenticationConverter()));
    }
}
