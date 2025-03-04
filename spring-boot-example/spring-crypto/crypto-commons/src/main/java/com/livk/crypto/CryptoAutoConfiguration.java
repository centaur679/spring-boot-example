/*
 * Copyright 2021-2024 spring-boot-extension the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.livk.crypto;

import com.livk.auto.service.annotation.SpringAutoService;
import com.livk.crypto.fotmat.CryptoFormatter;
import com.livk.crypto.fotmat.SpringAnnotationFormatterFactory;
import com.livk.crypto.support.AesSecurity;
import com.livk.crypto.support.PbeSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;

/**
 * @author livk
 */
@SpringAutoService
@AutoConfiguration
@EnableConfigurationProperties(CryptoProperties.class)
public class CryptoAutoConfiguration {

	@Bean
	@ConditionalOnProperty(prefix = CryptoProperties.PREFIX, name = "type", havingValue = "AES")
	public AesSecurity aesSecurity(@Value("${spring.crypto.metadata.salt}") String salt)
			throws NoSuchAlgorithmException {
		return new AesSecurity(salt);
	}

	@Bean
	@ConditionalOnProperty(prefix = CryptoProperties.PREFIX, name = "type", havingValue = "PBE")
	public PbeSecurity pbeSecurity(@Value("${spring.crypto.metadata.salt}") String salt,
			@Value("${spring.crypto.metadata.password}") String password,
			@Value("${spring.crypto.metadata.iterationCount:1000}") Integer iterationCount)
			throws GeneralSecurityException {
		return new PbeSecurity(salt, password, iterationCount);
	}

	@AutoConfiguration
	@RequiredArgsConstructor
	@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
	public static class CryptoMvcAutoConfiguration implements WebMvcConfigurer {

		private final ObjectProvider<CryptoFormatter<?>> cryptoFormatters;

		@Override
		public void addFormatters(FormatterRegistry registry) {
			registry.addFormatterForFieldAnnotation(new SpringAnnotationFormatterFactory(cryptoFormatters));
		}

	}

	@AutoConfiguration
	@RequiredArgsConstructor
	@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
	public static class CryptoWebfluxAutoConfiguration implements WebFluxConfigurer {

		private final ObjectProvider<CryptoFormatter<?>> cryptoFormatters;

		@Override
		public void addFormatters(FormatterRegistry registry) {
			registry.addFormatterForFieldAnnotation(new SpringAnnotationFormatterFactory(cryptoFormatters));
		}

	}

}
