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

package com.livk.autoconfigure.redisson;

import com.livk.auto.service.annotation.SpringAutoService;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * The type Redisson auto configuration.
 *
 * @author livk
 */
@SpringAutoService
@ConditionalOnClass(Redisson.class)
@AutoConfiguration(before = RedisAutoConfiguration.class)
@EnableConfigurationProperties({ ConfigProperties.class, RedisProperties.class })
public class RedissonAutoConfiguration {

	@Bean
	public CustomEditorConfigurer customEditorConfigurer() {
		CustomEditorConfigurer configurer = new CustomEditorConfigurer();
		configurer.setPropertyEditorRegistrars(new PropertyEditorRegistrar[] { new RedissonPropertyEditorRegistrar() });
		return configurer;
	}

	/**
	 * Redisson client redisson client.
	 * @param configProperties the config properties
	 * @param redisProperties the redis properties
	 * @param configCustomizers the config customizers
	 * @return the redisson client
	 */
	@Bean(destroyMethod = "shutdown")
	@ConditionalOnMissingBean
	public RedissonClient redissonClient(ConfigProperties configProperties, RedisProperties redisProperties,
			ObjectProvider<ConfigCustomizer> configCustomizers) {
		return RedissonClientFactory.create(configProperties, redisProperties, configCustomizers);
	}

	/**
	 * Redisson connection factory redisson connection factory.
	 * @param redisson the redisson
	 * @return the redisson connection factory
	 */
	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnClass(RedissonConnectionFactory.class)
	public RedissonConnectionFactory redissonConnectionFactory(RedissonClient redisson) {
		return new RedissonConnectionFactory(redisson);
	}

}
