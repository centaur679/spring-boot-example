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

package com.livk.context.disruptor.factory;

import com.livk.context.disruptor.support.DisruptorEventConsumer;
import com.livk.context.disruptor.support.DisruptorEventWrapper;
import com.livk.context.disruptor.support.SpringDisruptor;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;

import java.util.concurrent.ThreadFactory;

/**
 * @author livk
 */
public class DisruptorFactoryBean<T>
		implements FactoryBean<SpringDisruptor<T>>, BeanFactoryAware, InitializingBean, DisposableBean {

	@Setter
	private AnnotationAttributes attributes;

	private BeanFactory beanFactory;

	private SpringDisruptor<T> disruptor;

	@Setter
	private Class<T> type;

	@Override
	public SpringDisruptor<T> getObject() {
		return disruptor;
	}

	@Override
	public Class<?> getObjectType() {
		return SpringDisruptor.class;
	}

	@Override
	public void setBeanFactory(@NonNull BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	@Override
	public void afterPropertiesSet() {
		SpringEventFactory<T> factory = new SpringEventFactory<>();
		int bufferSize = attributes.getNumber("bufferSize").intValue();
		ProducerType producerType = attributes.getEnum("type");
		WaitStrategy waitStrategy = this.createInstance("strategyBeanName", "strategy", WaitStrategy.class);
		ThreadFactory threadFactory = this.createThreadFactory();
		disruptor = new SpringDisruptor<>(factory, bufferSize, threadFactory, producerType, waitStrategy);
		disruptor.handleEventsWith(createEventHandler(beanFactory, type));
		disruptor.start();
	}

	private EventHandler<DisruptorEventWrapper<T>> createEventHandler(BeanFactory beanFactory, Class<T> type) {
		ResolvableType resolvableType = ResolvableType.forClassWithGenerics(DisruptorEventConsumer.class, type);
		ObjectProvider<DisruptorEventConsumer<T>> disruptorEventConsumers = beanFactory.getBeanProvider(resolvableType);
		return new AggregateEventHandlerProvider<>(disruptorEventConsumers);
	}

	private ThreadFactory createThreadFactory() {
		ThreadFactory threadFactory = this.createInstance("threadFactoryBeanName", "threadFactory",
				ThreadFactory.class);
		if (attributes.getBoolean("useVirtualThreads")) {
			return new VirtualThreadFactory(threadFactory);
		}
		return threadFactory;
	}

	private <S> S createInstance(String beanNameKey, String classKey, Class<S> targetType) {
		String beanName = attributes.getString(beanNameKey);
		if (StringUtils.hasText(beanName)) {
			return beanFactory.getBean(beanName, targetType);
		}
		Class<? extends S> clazz = attributes.getClass(classKey);
		return BeanUtils.instantiateClass(clazz);
	}

	@Override
	public void destroy() {
		disruptor.shutdown();
	}

	private static class SpringEventFactory<T> implements EventFactory<DisruptorEventWrapper<T>> {

		public DisruptorEventWrapper<T> newInstance() {
			return new DisruptorEventWrapper<>();
		}

	}

	@RequiredArgsConstructor
	private static final class AggregateEventHandlerProvider<T> implements EventHandler<DisruptorEventWrapper<T>> {

		private final ObjectProvider<DisruptorEventConsumer<T>> consumerObjectProvider;

		@Override
		public void onEvent(DisruptorEventWrapper<T> event, long sequence, boolean endOfBatch) throws Exception {
			for (DisruptorEventConsumer<T> eventConsumer : consumerObjectProvider) {
				eventConsumer.onEvent(event.unwrap(), sequence, endOfBatch);
			}
		}

		@Override
		public void onStart() {
			for (EventHandler<T> eventHandler : consumerObjectProvider) {
				eventHandler.onStart();
			}
		}

		@Override
		public void onShutdown() {
			for (EventHandler<T> eventHandler : consumerObjectProvider) {
				eventHandler.onShutdown();
			}
		}

	}

	@RequiredArgsConstructor
	private static class VirtualThreadFactory implements ThreadFactory {

		private final ThreadFactory delegate;

		@Override
		public Thread newThread(@NonNull Runnable r) {
			Thread thread = delegate.newThread(r);
			return Thread.ofVirtual()
				.name("virtual-" + thread.getName())
				.inheritInheritableThreadLocals(true)
				.unstarted(thread);
		}

	}

}
