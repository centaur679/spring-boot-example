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

package com.livk.commons.spring.context;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * <p>
 * AbstractImportSelectorTest
 * </p>
 *
 * @author livk
 */
class AbstractImportSelectorTest {

	@Test
	public void testFindAnnotation() {
		MyAnnotationImportSelector selector = new MyAnnotationImportSelector();
		assertEquals(MyAnnotation.class, selector.annotationClass);
	}

	@interface MyAnnotation {

	}

	static class MyAnnotationImportSelector extends AbstractImportSelector<MyAnnotation> {

	}

}
