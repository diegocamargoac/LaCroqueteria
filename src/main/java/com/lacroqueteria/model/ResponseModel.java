package com.lacroqueteria.model;

public class ResponseModel<T> {

	private boolean success;
	private String message;
	private T data;
	
	public boolean isSuccess() {
		return success;
	}
	
	public void setSuccess(boolean sucess) {
		this.success = sucess;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	
	public void setData(T data) {
		this.data = data;
	}
	
	public ResponseModel(boolean success, String message, T data) {
		this.success = success;
		this.message = message;
		this.data = data;
	}
	
	public ResponseModel() {
		
	}
	
}
