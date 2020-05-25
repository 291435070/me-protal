package com.protal.me.util;

import java.time.LocalDateTime;

public class ResultBody {

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

	public static enum StatusEnum {

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

}