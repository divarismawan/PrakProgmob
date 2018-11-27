package com.example.fx504.praktikum.model;

import com.google.gson.annotations.SerializedName;

public class ResponseLogin{

	@SerializedName("user_email")
	private String userEmail;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("user_name")
	private String userName;

	@SerializedName("user_tlfn")
	private String userTlfn;

	@SerializedName("status")
	private int status;

	@SerializedName("token")
	private String token;

	public void setUserEmail(String userEmail){
		this.userEmail = userEmail;
	}

	public String getUserEmail(){
		return userEmail;
	}

	public void setUserId(int userId){
		this.userId = userId;
	}

	public int getUserId(){
		return userId;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getUserName(){
		return userName;
	}

	public void setUserTlfn(String userTlfn){
		this.userTlfn = userTlfn;
	}

	public String getUserTlfn(){
		return userTlfn;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}

	public void setToken(String token){
		this.token = token;
	}

	public String getToken(){
		return token;
	}

	@Override
 	public String toString(){
		return 
			"ResponseLogin{" + 
			"user_email = '" + userEmail + '\'' + 
			",user_id = '" + userId + '\'' + 
			",user_name = '" + userName + '\'' + 
			",user_tlfn = '" + userTlfn + '\'' + 
			",status = '" + status + '\'' + 
			",token = '" + token + '\'' + 
			"}";
		}
}