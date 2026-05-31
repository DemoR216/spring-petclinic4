/*
 * Copyright 2012-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Global exception handler that catches common exceptions and renders a friendly error
 * page instead of exposing raw stack traces to the user.
 *
 * @author Devin AI
 */
@ControllerAdvice
class GlobalExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(IllegalArgumentException.class)
	public ModelAndView handleIllegalArgument(IllegalArgumentException ex) {
		log.warn("Bad request: {}", ex.getMessage());
		return buildErrorView(HttpStatus.BAD_REQUEST, ex.getMessage());
	}

	@ExceptionHandler(RuntimeException.class)
	public ModelAndView handleRuntimeException(RuntimeException ex) {
		log.error("Unexpected error", ex);
		return buildErrorView(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public ModelAndView handleGenericException(Exception ex) {
		log.error("Unhandled exception", ex);
		return buildErrorView(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
	}

	private ModelAndView buildErrorView(HttpStatus status, String message) {
		ModelAndView mav = new ModelAndView("error");
		mav.addObject("status", status.value());
		mav.addObject("message", message);
		mav.setStatus(status);
		return mav;
	}

}
