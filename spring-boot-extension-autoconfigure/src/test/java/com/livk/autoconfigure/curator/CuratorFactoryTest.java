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

package com.livk.autoconfigure.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author livk
 */
class CuratorFactoryTest {

	static CuratorProperties properties = new CuratorProperties();

	@Test
	void create() throws InterruptedException {
		RetryPolicy retryPolicy = CuratorFactory.retryPolicy(properties);
		CuratorFramework framework = CuratorFactory.create(properties, retryPolicy, () -> null, () -> null, () -> null);
		assertNotNull(framework);
	}

	@Test
	void retryPolicy() {
		assertInstanceOf(ExponentialBackoffRetry.class, CuratorFactory.retryPolicy(properties));
	}

}
