package com.lacroqueteria.model;

public class ResponseModel<T> {

	private boolean sucess;
	private String message;
	private T data;
	
	public boolean isSucess() {
		return sucess;
	}
	public void setSucess(boolean sucess) {
		this.sucess = sucess;
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
	public ResponseModel(boolean sucess, String message, T data) {
		this.sucess = sucess;
		this.message = message;
		this.data = data;
	}
	
	public ResponseModel() {
	}
	
}
