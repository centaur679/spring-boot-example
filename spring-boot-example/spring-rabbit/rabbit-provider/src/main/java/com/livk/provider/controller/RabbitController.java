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

package com.livk.provider.controller;

import com.livk.amqp.entity.Message;
import com.livk.commons.jackson.util.JsonMapperUtils;
import com.livk.provider.send.RabbitSend;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * RabbitController
 * </p>
 *
 * @author livk
 */
@RestController
@RequestMapping("provider")
@RequiredArgsConstructor
public class RabbitController {

	private final RabbitSend send;

	@PostMapping("sendMsgDirect")
	public void sendMsgDirect(@RequestBody Message<String> message) {
		send.sendMsgDirect(message);
	}

	@PostMapping("sendMsgFanout")
	public void sendMsgFanout(@RequestBody Message<String> message) {
		send.sendMsgFanout(message);
	}

	@PostMapping("/sendMsgTopic/{key}")
	public void sendMsgTopic(@RequestBody Message<String> message, @PathVariable String key) {
		send.sendMsgTopic(message, key);
	}

	@PostMapping("sendMsgHeaders")
	public void sendMsgHeaders(@RequestBody Message<String> message, @RequestParam String json) {
		send.sendMsgHeaders(message, JsonMapperUtils.readValueMap(json, String.class, Object.class));
	}

}
