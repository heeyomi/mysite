package com.douzone.mysite.dto;

public class JsonResult {
	private String result;  // success 이거나 fail
	private Object data;    //성공이면 set data, fail이면 null임
	private String message; // result가 fail이면 message가 set
	
	private JsonResult() {
		
	}

	private JsonResult(Object data) {
		result = "success";
		this.data = data;
	}

	private JsonResult(String message) {
		result = "fail";
		this.message = message;
	}
	
	public String getResult() {
		return result;
	}
	
	public Object getData() {
		return data;
	}

	public String getMessage() {
		return message;
	}
	
	@Override
	public String toString() {
		return "JsonResult [result=" + result + ", data=" + data + ", message=" + message + "]";
	}

	public static JsonResult success(Object data) {
		return new JsonResult(data);
	}

	public static JsonResult fail(String message) {
		return new JsonResult(message);
	}
	
	
	
}
