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

package com.livk.redis.submit.support;

import com.livk.commons.spring.context.SpringContextHolder;
import com.livk.core.lock.DistributedLock;
import com.livk.core.lock.LockType;
import com.livk.core.lock.support.RedissonLock;
import lombok.experimental.UtilityClass;

/**
 * @author livk
 */
@UtilityClass
public class LockSupport {

	private static final DistributedLock LOCK;

	static {
		LOCK = SpringContextHolder.getBean(RedissonLock.class);
	}

	public boolean tryLock(LockType type, String key, long leaseTime, long waitTime, boolean async) {
		return LOCK.tryLock(type, key, leaseTime, waitTime, async);
	}

	public void unlock() {
		LOCK.unlock();
	}

}
