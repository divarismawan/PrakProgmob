package com.example.fx504.praktikum.model;

import com.google.gson.annotations.SerializedName;

public class ResGetById{

	@SerializedName("novel_status")
	private String novelStatus;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("novel_title")
	private String novelTitle;

	@SerializedName("novel_story")
	private String novelStory;

	@SerializedName("novel_genre")
	private String novelGenre;

	@SerializedName("novel_cover")
	private String novelCover;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("novel_synopsis")
	private String novelSynopsis;

	public void setNovelStatus(String novelStatus){
		this.novelStatus = novelStatus;
	}

	public String getNovelStatus(){
		return novelStatus;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setNovelTitle(String novelTitle){
		this.novelTitle = novelTitle;
	}

	public String getNovelTitle(){
		return novelTitle;
	}

	public void setNovelStory(String novelStory){
		this.novelStory = novelStory;
	}

	public String getNovelStory(){
		return novelStory;
	}

	public void setNovelGenre(String novelGenre){
		this.novelGenre = novelGenre;
	}

	public String getNovelGenre(){
		return novelGenre;
	}

	public void setNovelCover(String novelCover){
		this.novelCover = novelCover;
	}

	public String getNovelCover(){
		return novelCover;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setNovelSynopsis(String novelSynopsis){
		this.novelSynopsis = novelSynopsis;
	}

	public String getNovelSynopsis(){
		return novelSynopsis;
	}

	@Override
 	public String toString(){
		return 
			"ResGetById{" + 
			"novel_status = '" + novelStatus + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",novel_title = '" + novelTitle + '\'' + 
			",novel_story = '" + novelStory + '\'' + 
			",novel_genre = '" + novelGenre + '\'' + 
			",novel_cover = '" + novelCover + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			",novel_synopsis = '" + novelSynopsis + '\'' + 
			"}";
		}
}