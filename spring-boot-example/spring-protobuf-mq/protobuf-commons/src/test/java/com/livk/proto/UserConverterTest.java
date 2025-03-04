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

package com.livk.proto;

import com.livk.commons.util.ObjectUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author livk
 */
class UserConverterTest {

	static final UserConverter converter = UserConverter.INSTANCE;

	@Test
	void convert() {
		User user = new User(1L, "root", "123456@gmail.com", 0);

		byte[] bytes = converter.convert(user);
		assertNotNull(bytes);
		assertFalse(ObjectUtils.isEmpty(bytes));
		assertEquals(user, converter.convert(bytes));
	}

}
