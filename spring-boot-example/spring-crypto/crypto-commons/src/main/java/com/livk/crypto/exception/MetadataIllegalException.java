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

package com.livk.crypto.exception;

import lombok.Getter;

/**
 * The type Metadata illegal exception.
 */
public class MetadataIllegalException extends RuntimeException {

	@Getter
	private final String action;

	/**
	 * Instantiates a new Metadata illegal exception.
	 * @param message the message
	 */
	public MetadataIllegalException(String message, String action) {
		super(message);
		this.action = action;
	}

}
