package com.bit.mini.dto;

import java.sql.Date;

public class FridgeDto {
	
	private int ingredient_id;
	private int user_id;
	private String ingredient_name;
	private float quantity;
	private String unit;
	private Date expiration_date;
	private Date created_at;
	
	public FridgeDto() {
		
	}
	
	public FridgeDto(int ingredient_id, int user_id, String ingredient_name, float quantity, String unit, Date expiration_date, Date created_at) {
		this.ingredient_id = ingredient_id;
		this.user_id = user_id;
		this.ingredient_name = ingredient_name;
		this.quantity = quantity;
		this.unit = unit;
		this.expiration_date = expiration_date;
		this.created_at = created_at;
	}

	public int getIngredient_id() {
		return ingredient_id;
	}

	public void setIngredient_id(int ingredient_id) {
		this.ingredient_id = ingredient_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getIngredient_name() {
		return ingredient_name;
	}

	public void setIngredient_name(String ingredient_name) {
		this.ingredient_name = ingredient_name;
	}

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Date getExpiration_date() {
		return expiration_date;
	}

	public void setExpiration_date(Date expiration_date) {
		this.expiration_date = expiration_date;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
	
}
