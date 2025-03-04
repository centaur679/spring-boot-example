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

package com.livk.context.disruptor.factory;

import com.livk.commons.util.AnnotationUtils;
import com.livk.context.disruptor.DisruptorConfig;
import com.livk.context.disruptor.Entity;
import com.livk.context.disruptor.annotation.DisruptorEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author livk
 */
@SpringJUnitConfig(DisruptorConfig.class)
class DisruptorFactoryBeanTest {

	@Autowired
	BeanFactory beanFactory;

	@Test
	void test() {
		DisruptorFactoryBean<Entity> factoryBean = new DisruptorFactoryBean<>();
		AnnotationMetadata metadata = AnnotationMetadata.introspect(Entity.class);
		AnnotationAttributes attributes = AnnotationUtils.attributesFor(metadata, DisruptorEvent.class);
		factoryBean.setType(Entity.class);
		factoryBean.setAttributes(attributes);
		factoryBean.setBeanFactory(beanFactory);
		factoryBean.afterPropertiesSet();
		assertNotNull(factoryBean.getObject());
	}

}
