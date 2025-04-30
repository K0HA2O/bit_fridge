package com.bit.mini.MyHealthCommand;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.bit.mini.Controller.Command;
import com.bit.mini.dao.HealthDao;
import com.bit.mini.dto.HealthDataDto;

@Component("registerBloodSugar")
public class RegisterBloodSugar implements Command {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public void execute(Model model) {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		HttpSession session = request.getSession();
		HealthDao dao = sqlSession.getMapper(HealthDao.class);
		
		int userId = (int) session.getAttribute("userId");
		
		String mealTime = request.getParameter("mealTime");
		String mealStatus = request.getParameter("mealStatus");
		float bloodSugarLevel = Float.parseFloat(request.getParameter("bloodSugarLevel"));
		
		Date date = new Date(System.currentTimeMillis());
		
		HealthDataDto existingData = dao.getHealthDataByMealTimeAndStatus(userId, mealTime, mealStatus, date);
		
		HealthDataDto dto = new HealthDataDto();
		dto.setUser_id(userId);
		dto.setMeal_time(HealthDataDto.Meal_time.valueOf(mealTime));
		dto.setMeal_status(HealthDataDto.Meal_status.valueOf(mealStatus));
		dto.setBlood_sugar_level(bloodSugarLevel);
		dto.setDate(date);
		dto.setCreated_at(new Timestamp(System.currentTimeMillis()));
		
		if (existingData != null) {
			dao.updateBloodSugarData(dto);
		} else {
			dao.insertBloodSugarData(dto);
		}
	}
}
