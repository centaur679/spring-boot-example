/*
 * Copyright 2021 spring-boot-extension the original author or authors.
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
 *
 */

package com.livk.env;

import com.livk.auto.service.annotation.SpringFactories;
import com.livk.commons.jackson.util.JsonMapperUtils;
import com.livk.commons.util.YamlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/**
 * <p>
 * 实现EnvironmentPostProcessor需要通过SPI来进行注册
 * </p>
 *
 * @author livk
 */
@Slf4j
@SpringFactories
public class DynamicEnvironment implements EnvironmentPostProcessor {

    private static final String source = "env.json";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        MutablePropertySources propertySources = environment.getPropertySources();
        try {
            ClassPathResource resource = new ClassPathResource(source);
            if (resource.exists()) {
                InputStream inputStream = resource.getInputStream();
                Map<String, Object> map = JsonMapperUtils.readValueMap(inputStream, String.class, Object.class);
                Properties properties = YamlUtils.ymlMapToMap(map);
                PropertiesPropertySource livkSource = new PropertiesPropertySource("livkSource", properties);
                propertySources.addLast(livkSource);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
