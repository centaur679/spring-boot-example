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

package com.livk.commons.aop;

import org.junit.jupiter.api.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * <p>
 * AnnotationClassOrMethodPointcutTest
 * </p>
 *
 * @author livk
 */
class AnnotationClassOrMethodPointcutTest {

	@Test
	void test() throws NoSuchMethodException {
		AnnotationClassOrMethodPointcut pointcut = new AnnotationClassOrMethodPointcut(MyAnnotation.class);

		assertTrue(pointcut.getClassFilter().matches(AopProxyClass.class));
		assertTrue(pointcut.getMethodMatcher()
			.matches(AopProxyClass.class.getDeclaredMethod("testAop"), AopProxyClass.class));
	}

	@MyAnnotation
	static class AopProxyClass {

		@MyAnnotation
		void testAop() {
		}

	}

	@Target({ ElementType.TYPE, ElementType.METHOD })
	@Retention(RetentionPolicy.RUNTIME)
	@interface MyAnnotation {

	}

}
