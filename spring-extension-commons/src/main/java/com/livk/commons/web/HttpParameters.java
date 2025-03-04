/*
 * Copyright 2021-2024 spring-boot-extension the original author or authors.
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

package com.livk.commons.web;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * <p>
 * 根据{@link org.springframework.http.HttpHeaders}改造
 * <p>
 * 用于HttpParam，适用于Query和FORM请求数据存储
 * </p>
 *
 * @author livk
 * @see org.springframework.http.HttpHeaders
 * @since 1.3.5
 */
public final class HttpParameters implements MultiValueMap<String, String>, Serializable {

	@Serial
	private static final long serialVersionUID = -578554704772377436L;

	final MultiValueMap<String, String> parameters;

	public HttpParameters() {
		this(CollectionUtils.toMultiValueMap(new LinkedMultiValueMap<>(8)));
	}

	public HttpParameters(MultiValueMap<String, String> parameters) {
		Assert.notNull(parameters, "MultiValueMap must not be null");
		this.parameters = parameters;
	}

	public HttpParameters(Map<String, List<String>> map) {
		this(CollectionUtils.toMultiValueMap(map));
	}

	public List<String> getOrEmpty(Object parameterName) {
		List<String> values = get(parameterName);
		return (values != null ? values : Collections.emptyList());
	}

	@Override
	@Nullable
	public String getFirst(@NonNull String parameterName) {
		return this.parameters.getFirst(parameterName);
	}

	@Override
	public void add(@NonNull String parameterName, @Nullable String parameterValue) {
		this.parameters.add(parameterName, parameterValue);
	}

	@Override
	public void addAll(@NonNull String key, @NonNull List<? extends String> values) {
		this.parameters.addAll(key, values);
	}

	@Override
	public void addAll(@NonNull MultiValueMap<String, String> values) {
		this.parameters.addAll(values);
	}

	@Override
	public void set(@NonNull String parameterName, @Nullable String parameterValue) {
		this.parameters.set(parameterName, parameterValue);
	}

	@Override
	public void setAll(@NonNull Map<String, String> values) {
		this.parameters.setAll(values);
	}

	@NonNull
	@Override
	public Map<String, String> toSingleValueMap() {
		return this.parameters.toSingleValueMap();
	}

	@Override
	public int size() {
		return this.parameters.size();
	}

	@Override
	public boolean isEmpty() {
		return this.parameters.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return this.parameters.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return this.parameters.containsValue(value);
	}

	@Override
	@Nullable
	public List<String> get(Object key) {
		return this.parameters.get(key);
	}

	@Override
	public List<String> put(String key, List<String> value) {
		return this.parameters.put(key, value);
	}

	@Override
	public List<String> remove(Object key) {
		return this.parameters.remove(key);
	}

	@Override
	public void putAll(@NonNull Map<? extends String, ? extends List<String>> map) {
		this.parameters.putAll(map);
	}

	@Override
	public void clear() {
		this.parameters.clear();
	}

	@NonNull
	@Override
	public Set<String> keySet() {
		return this.parameters.keySet();
	}

	@NonNull
	@Override
	public Collection<List<String>> values() {
		return this.parameters.values();
	}

	@NonNull
	@Override
	public Set<Entry<String, List<String>>> entrySet() {
		return this.parameters.entrySet();
	}

	@Override
	public void forEach(BiConsumer<? super String, ? super List<String>> action) {
		this.parameters.forEach(action);
	}

	@Override
	public List<String> putIfAbsent(String key, List<String> value) {
		return this.parameters.putIfAbsent(key, value);
	}

	@Override
	public boolean equals(@Nullable Object other) {
		return (this == other || (other instanceof HttpParameters that && unwrap(this).equals(unwrap(that))));
	}

	@Override
	public int hashCode() {
		return this.parameters.hashCode();
	}

	@Override
	public String toString() {
		return formatParameters(this.parameters);
	}

	private static MultiValueMap<String, String> unwrap(HttpParameters parameters) {
		while (parameters.parameters instanceof HttpParameters httpParameters) {
			parameters = httpParameters;
		}
		return parameters.parameters;
	}

	public static String formatParameters(MultiValueMap<String, String> parameters) {
		return parameters.entrySet().stream().map(entry -> {
			List<String> values = entry.getValue();
			return entry.getKey() + ":" + (values.size() == 1 ? "\"" + values.getFirst() + "\""
					: values.stream().map(s -> "\"" + s + "\"").collect(Collectors.joining(", ")));
		}).collect(Collectors.joining(", ", "[", "]"));
	}

}
