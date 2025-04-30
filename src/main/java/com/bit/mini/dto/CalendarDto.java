package com.bit.mini.dto;

import java.sql.Date;

public class CalendarDto {
	
	private int id;
	private String recipe_id;
	private String recipe_title;
	private Date event_date;
	private int user_id;
	
	public CalendarDto() {
		
	}
	
	public CalendarDto(int id, String recipe_id, String recipe_title, Date event_date, int user_id) {
		this.id = id;
		this.recipe_id = recipe_id;
		this.recipe_title = recipe_title;
		this.event_date = event_date;
		this.user_id = user_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Date getEvent_date() {
		return event_date;
	}

	public void setEvent_date(Date event_date) {
		this.event_date = event_date;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
}
