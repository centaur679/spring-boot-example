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

package com.livk.event.context;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * InMemory
 * </p>
 *
 * @author livk
 */
@Component
public class InMemorySseEmitterRepository implements SseEmitterRepository<String> {

	private static final Map<String, SseEmitter> sseEmitters = new ConcurrentHashMap<>();

	@Override
	public Map<String, SseEmitter> all() {
		return sseEmitters;
	}

	@Override
	public SseEmitter get(String id) {
		return sseEmitters.get(id);
	}

	@Override
	public SseEmitter put(String id, SseEmitter sseEmitter) {
		return sseEmitters.put(id, sseEmitter);
	}

	@Override
	public SseEmitter remove(String id) {
		return sseEmitters.remove(id);
	}

}
