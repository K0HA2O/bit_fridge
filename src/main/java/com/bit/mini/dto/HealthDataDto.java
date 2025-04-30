package com.bit.mini.dto;

import java.sql.Date;
import java.sql.Timestamp;

public class HealthDataDto {
	
	private int id;
	private int user_id;
	private float blood_sugar_level;
	private Date date;
	private Meal_time meal_time;
	private Meal_status meal_status;
	private Timestamp created_at;
	
	public enum Meal_time {
		아침, 점심, 저녁;
	}
	
	public enum Meal_status {
		전, 후;
	}
	
	public HealthDataDto () {
		
	}
	
	public HealthDataDto(int id, int user_id, float blood_sugar_level, Date date, Meal_time meal_time, Meal_status meal_status, Timestamp created_at) {
		this.id = id;
		this.user_id = user_id;
		this.blood_sugar_level = blood_sugar_level;
		this.date = date;
		this.meal_time = meal_time;
		this.meal_status = meal_status;
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

	public float getBlood_sugar_level() {
		return blood_sugar_level;
	}

	public void setBlood_sugar_level(float blood_sugar_level) {
		this.blood_sugar_level = blood_sugar_level;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Meal_time getMeal_time() {
		return meal_time;
	}

	public void setMeal_time(Meal_time meal_time) {
		this.meal_time = meal_time;
	}

	public Meal_status getMeal_status() {
		return meal_status;
	}

	public void setMeal_status(Meal_status meal_status) {
		this.meal_status = meal_status;
	}

	public Timestamp getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}
}
