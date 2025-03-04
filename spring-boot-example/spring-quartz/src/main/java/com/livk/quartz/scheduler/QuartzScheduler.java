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

package com.livk.quartz.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * <p>
 * QuartzScheduler
 * </p>
 *
 * @author livk
 */
@Slf4j
public class QuartzScheduler extends QuartzJobBean {

	private void before() {
		log.info("before");
	}

	@Override
	protected void executeInternal(JobExecutionContext context) {
		before();
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		log.info("{}", jobDataMap.getString("user"));
		after();
	}

	private void after() {
		log.info("after");
	}

}
