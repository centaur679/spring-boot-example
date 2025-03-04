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

package com.livk.postgres.json.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.livk.commons.jackson.util.JsonMapperUtils;
import com.livk.postgres.json.entity.User;
import com.livk.postgres.json.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * UserController
 * </p>
 *
 * @author livk
 */
@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserMapper userMapper;

	@PostMapping("user")
	public HttpEntity<Boolean> save() {
		String json = """
				{
				  "mark": "livk"
				}""";
		User user = new User();
		user.setUsername("admin");
		user.setPassword("admin");
		user.setDes(JsonMapperUtils.readValue(json, JsonNode.class));
		return ResponseEntity.ok(userMapper.insert(user) != 0);
	}

	@GetMapping("user")
	public HttpEntity<List<User>> users() {
		return ResponseEntity.ok(userMapper.selectList());
	}

}
