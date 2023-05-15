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

package com.livk.autoconfigure.http.factory;

import com.livk.autoconfigure.http.ClassPathHttpScanner;
import com.livk.autoconfigure.http.annotation.HttpProvider;
import lombok.Setter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * HttpFactory
 * </p>
 *
 * @author livk
 */
@Setter
public class HttpServiceRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, BeanFactoryAware, EnvironmentAware {

    private ResourceLoader resourceLoader;

    private BeanFactory beanFactory;

    private Environment environment;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        List<String> packages = AutoConfigurationPackages.get(beanFactory);
        ClassPathHttpScanner scanner = new ClassPathHttpScanner(registry, environment, resourceLoader);
        scanner.registerFilters(HttpProvider.class);
        scanner.setBeanFactory(beanFactory);
        scanner.scan(StringUtils.toStringArray(packages));
    }

}
