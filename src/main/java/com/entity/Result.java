package com.entity;

import lombok.ToString;

@ToString
public class Result<T> {

	private Integer code;
	private String message;
	private Novel novel;
	private T date;

	public Result() {}

	public Result(T date) {	this.date = date;}

	public Result(Integer code, T date, String message, Novel novel) {
		this.code = code;
		this.date = date;
		this.novel = novel;
		this.message = message;
	}

	public T getDate() {
		return date;
	}

	public void setDate(T date) {
		this.date = date;
	}
	public Integer getCode() {
		return code == null ? 404 : code;
	}

	public String getMessage() {
		return message;
	}

	public Novel getNovel() {
		return novel;
	}

	public void setNovel(Novel novel) {
		this.novel = novel;
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
