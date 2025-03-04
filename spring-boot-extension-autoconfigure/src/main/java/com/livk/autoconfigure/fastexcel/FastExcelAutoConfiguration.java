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

package com.livk.autoconfigure.fastexcel;

import cn.idev.excel.FastExcel;
import com.livk.auto.service.annotation.SpringAutoService;
import com.livk.context.fastexcel.resolver.ExcelMethodArgumentResolver;
import com.livk.context.fastexcel.resolver.ExcelMethodReturnValueHandler;
import com.livk.context.fastexcel.resolver.ReactiveExcelMethodArgumentResolver;
import com.livk.context.fastexcel.resolver.ReactiveExcelMethodReturnValueHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author livk
 */
@AutoConfiguration
@SpringAutoService
@ConditionalOnClass(FastExcel.class)
public class FastExcelAutoConfiguration {

	/**
	 * The type Excel web mvc auto configuration.
	 */
	@AutoConfiguration
	@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
	public static class FastExcelWebMvcAutoConfiguration implements WebMvcConfigurer {

		@Override
		public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
			resolvers.add(new ExcelMethodArgumentResolver());
		}

		@Override
		public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
			handlers.add(new ExcelMethodReturnValueHandler());
		}

	}

	/**
	 * The type Excel web flux auto configuration.
	 */
	@AutoConfiguration
	@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
	public static class FastExcelWebFluxAutoConfiguration implements WebFluxConfigurer {

		/**
		 * Reactive excel method return value handler reactive excel method return value
		 * handler.
		 * @return the reactive excel method return value handler
		 */
		@Bean
		public ReactiveExcelMethodReturnValueHandler reactiveExcelMethodReturnValueHandler() {
			return new ReactiveExcelMethodReturnValueHandler();
		}

		@Override
		public void configureArgumentResolvers(ArgumentResolverConfigurer configurer) {
			configurer.addCustomResolver(new ReactiveExcelMethodArgumentResolver());
		}

	}

}
