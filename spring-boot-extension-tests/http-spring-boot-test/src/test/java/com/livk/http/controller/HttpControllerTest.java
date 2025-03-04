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

package com.livk.http.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.SpringVersion;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <p>
 * HttpControllerTest
 * </p>
 *
 * @author livk
 */
@AutoConfigureMockMvc
@SpringBootTest(value = { "logging.level.org.springframework.web.reactive.function.client.WebClient = debug" },
		webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class HttpControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Test
	void getTest() throws Exception {
		mockMvc.perform(get("/get"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("spring-boot-version").value(SpringBootVersion.getVersion()))
			.andExpect(jsonPath("spring-version").value(SpringVersion.getVersion()))
			.andExpect(jsonPath("java-version").value(System.getProperty("java.version")));
	}

}
