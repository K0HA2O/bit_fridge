package com.bit.mini.dao;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bit.mini.dto.HealthDataDto;

public interface HealthDao {

	public List<HealthDataDto> getHealthData(@Param("user_id") int userId, @Param("meal_time") String mealTime, @Param("meal_status") String mealStatus);
	public void insertBloodSugarData(HealthDataDto healthData);
	public HealthDataDto getHealthDataByMealTimeAndStatus(@Param("user_id") int userId, @Param("meal_time") String mealTime, @Param("meal_status") String mealStatus, @Param("date") Date date);
	public void updateBloodSugarData(HealthDataDto healthData);
}
