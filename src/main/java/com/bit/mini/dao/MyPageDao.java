package com.bit.mini.dao;

import java.sql.Date;
import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.bit.mini.dto.CommunityPostDto;
import com.bit.mini.dto.MyRecipeDto;
import com.bit.mini.dto.UserDto;
import com.bit.mini.dto.UserDto.Gender;

public interface MyPageDao {

	public void insertMyRecipe(MyRecipeDto myRecipeDto);
	public ArrayList<MyRecipeDto> getMyRecipeList(int user_id);
	public void deleteMyRecipe(String id);
	public ArrayList<CommunityPostDto> getMyPost(int user_id);
	public void deleteMyPost(String id);
	public UserDto getMyInfo(int user_id);
	public void modifyInfo(@Param("username") String username,
	        @Param("name") String name,
	        @Param("email") String email,
	        @Param("birthdate") Date birthdate,
	        @Param("gender") Gender gender,
	        @Param("allergies") String allergies,
	        @Param("id") int id);
}
