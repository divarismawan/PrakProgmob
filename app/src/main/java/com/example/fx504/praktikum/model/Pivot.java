package com.example.fx504.praktikum.model;

import com.google.gson.annotations.SerializedName;

public class Pivot{

	@SerializedName("user_id")
	private int userId;

	@SerializedName("novel_id")
	private int novelId;

	public void setUserId(int userId){
		this.userId = userId;
	}

	public int getUserId(){
		return userId;
	}

	public void setNovelId(int novelId){
		this.novelId = novelId;
	}

	public int getNovelId(){
		return novelId;
	}

	@Override
 	public String toString(){
		return 
			"Pivot{" + 
			"user_id = '" + userId + '\'' + 
			",novel_id = '" + novelId + '\'' + 
			"}";
		}
}