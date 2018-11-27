package com.example.fx504.praktikum.model;

import com.google.gson.annotations.SerializedName;

public class ResponseApi{

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public void setSuccess(boolean success){
		this.success = success;
	}

	public boolean isSuccess(){
		return success;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	@Override
 	public String toString(){
		return 
			"ResponseApi{" + 
			"success = '" + success + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}