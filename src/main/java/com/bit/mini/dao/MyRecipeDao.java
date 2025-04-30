package com.bit.mini.dao;

import java.util.ArrayList;

import com.bit.mini.dto.FridgeDto;

public interface MyRecipeDao {
	
	public float getBloodSugarLevel(int user_id);
	public ArrayList<FridgeDto> getIngredients(int user_id);
	public String getAllergies(int user_id);

}
