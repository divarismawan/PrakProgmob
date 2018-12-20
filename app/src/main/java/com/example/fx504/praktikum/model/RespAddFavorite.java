package com.example.fx504.praktikum.model;

import com.google.gson.annotations.SerializedName;

public class RespAddFavorite{

	@SerializedName("succes")
	private boolean succes;

	@SerializedName("message")
	private String message;

	public void setSucces(boolean succes){
		this.succes = succes;
	}

	public boolean isSucces(){
		return succes;
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
			"RespAddFavorite{" + 
			"succes = '" + succes + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}