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

package com.livk.autoconfigure.http;

import com.livk.auto.service.annotation.SpringFactories;
import com.livk.context.http.exception.HttpServiceRegistrarException;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.boot.diagnostics.FailureAnalyzer;

/**
 * @author livk
 */
@SpringFactories(FailureAnalyzer.class)
public class HttpServiceRegistrarFailureAnalyzer extends AbstractFailureAnalyzer<HttpServiceRegistrarException> {

	@Override
	protected FailureAnalysis analyze(Throwable rootFailure, HttpServiceRegistrarException cause) {
		return new FailureAnalysis(cause.getMessage(),
				"请添加com.livk.context.http.factory.AdapterFactory实现，并添加至spring.factories", cause);
	}

}
