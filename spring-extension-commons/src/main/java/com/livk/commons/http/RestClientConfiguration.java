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

package com.livk.commons.http;

import com.livk.auto.service.annotation.SpringAutoService;
import com.livk.commons.http.annotation.EnableRestClient;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

/**
 * <p>
 * RestClientConfiguration
 * </p>
 *
 * @author livk
 */
@AutoConfiguration
@SpringAutoService(EnableRestClient.class)
@ImportAutoConfiguration(OkHttpClientConfiguration.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class RestClientConfiguration {

	/**
	 * Rest client
	 * @param builder builder
	 * @return client
	 */
	@Bean
	public RestClient restClient(RestClient.Builder builder) {
		return builder.build();
	}

	@Bean
	public RestClientCustomizer restClientCustomizer(ClientHttpRequestFactory requestFactory) {
		return builder -> builder.requestFactory(requestFactory);
	}

}
