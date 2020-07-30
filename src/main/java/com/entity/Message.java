package com.entity;

import lombok.ToString;

@ToString
public class Message {

	private Integer code;
	private String message;

	public Message() {
		this.code = null;
	}

	public Integer getCode() {
		return code == null ? 404 : code;
	}

	public String getMessage() {
		return message;
	}

	public void setCode(Integer code) {
		if (code.equals(null))
			this.code = 404;
		else
			this.code = code;
	}
	public void setMessage(String message) {
		this.message = message;
	}


}
