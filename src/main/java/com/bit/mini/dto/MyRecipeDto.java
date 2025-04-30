package com.bit.mini.dto;

import java.sql.Timestamp;

public class MyRecipeDto {

	private int id;
	private int user_id;
	private String recipe_id;
	private String recipe_title;
	private String recipe_image;
	private Timestamp created_at;
	
	public MyRecipeDto() {
		
	}
	
	public MyRecipeDto(int id, int user_id, String recipe_id, String recipe_title, String recipe_image, Timestamp created_at) {
		this.id = id;
		this.user_id = user_id;
		this.recipe_id = recipe_id;
		this.recipe_title = recipe_title;
		this.recipe_image = recipe_image;
		this.created_at = created_at;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getRecipe_id() {
		return recipe_id;
	}

	public void setRecipe_id(String recipe_id) {
		this.recipe_id = recipe_id;
	}

	public String getRecipe_title() {
		return recipe_title;
	}

	public void setRecipe_title(String recipe_title) {
		this.recipe_title = recipe_title;
	}

	public String getRecipe_image() {
		return recipe_image;
	}

	public void setRecipe_image(String recipe_image) {
		this.recipe_image = recipe_image;
	}

	public Timestamp getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}
	
}
