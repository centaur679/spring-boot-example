/*
 * Copyright 2021-2024 spring-boot-extension the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *       https://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.livk.commons.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * @author livk
 */
class GenericWrapperTest {

	@Test
	void test() {
		String value = "livk";
		StringBuilder builder = new StringBuilder(value).reverse();
		GenericWrapper<String> wrapper = GenericWrapper.of(value);
		assertEquals(value, wrapper.unwrap());

		GenericWrapper<String> map = GenericWrapper.of(reverse(wrapper.unwrap()));
		assertEquals(builder.toString(), map.unwrap());
		assertNotEquals(value, map.unwrap());

		GenericWrapper<String> flatmap = GenericWrapper.of(wrapper.unwrap());
		assertEquals(value, flatmap.unwrap());
	}

	String reverse(String str) {
		List<String> list = Arrays.asList(str.split(""));
		Collections.reverse(list);
		return String.join("", list);
	}

}
