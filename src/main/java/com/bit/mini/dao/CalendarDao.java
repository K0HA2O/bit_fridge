package com.bit.mini.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CalendarDao {
	
	public void addRecipeToCalendar(@Param("recipe_id") String recipe_id, @Param("recipe_title") String recipe_title, @Param("user_id") int user_id, @Param("event_date") String event_date);
	public List<Map<String, Object>> getAllRecipeEvents(int user_id);
	public void deleteEvent(@Param("user_id") int user_id, @Param("event_id") int event_id);
	public void updateEventDate(@Param("user_id") int user_id, @Param("event_id") int event_id, @Param("event_date") String event_date);
}
