package com.protal.me.config;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

class ResultBody {

	private int status;
	private String msg;
	private Object data;

	/**
	 * 返回成功结果
	 * @param statusEnum
	 * @param data
	 */
	public ResultBody(StatusEnum statusEnum, Object data) {
		this.status = statusEnum.getStatus();
		this.msg = statusEnum.getMsg();
		this.data = data;
	}

	/**
	 * 返回失败结果
	 * @param statusEnum
	 * @param data
	 * @param msg
	 */
	public ResultBody(StatusEnum statusEnum, Object data, String msg) {
		this.status = statusEnum.getStatus();
		this.msg = String.join(",", LocalDateTime.now().toString(), statusEnum.getMsg(), msg);
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}

enum StatusEnum {

	SUCCESS(0, "success"), FAILURE(1, "failure");

	private int status;
	private String msg;

	private StatusEnum(int status, String msg) {
		this.status = status;
		this.msg = msg;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}