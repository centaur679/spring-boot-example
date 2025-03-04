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

package com.livk.mybatisplugins.service.impl;

import com.livk.mybatisplugins.entity.User;
import com.livk.mybatisplugins.mapper.UserMapper;
import com.livk.mybatisplugins.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * UserServiceImpl
 * </p>
 *
 * @author livk
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserMapper userMapper;

	@Override
	public User getById(Integer id) {
		return userMapper.selectById(id);
	}

	@Override
	public boolean updateById(User user) {
		return userMapper.updateById(user) > 0;
	}

	@Override
	public boolean save(User user) {
		return userMapper.insert(user) > 0;
	}

	@Override
	public boolean deleteById(Integer id) {
		return userMapper.deleteById(id) > 0;
	}

	@Override
	public List<User> list() {
		return userMapper.list();
	}

}
