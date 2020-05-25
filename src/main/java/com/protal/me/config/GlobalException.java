package com.protal.me.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.protal.me.util.ResultBody;
import com.protal.me.util.ResultBody.StatusEnum;

@RestControllerAdvice
public class GlobalException {

	private static final Logger LOG = LoggerFactory.getLogger(GlobalException.class);

	@ExceptionHandler(value = NullPointerException.class)
	public Object handler(NullPointerException e) {
		LOG.error("NullPointerException", e);
		return new ResultBody(StatusEnum.FAILURE, null, "NPE");
	}

	@ExceptionHandler(value = Exception.class)
	public Object handler(Exception e) {
		LOG.error("Exception", e);
		return new ResultBody(StatusEnum.FAILURE, null, e.getMessage());
	}

}