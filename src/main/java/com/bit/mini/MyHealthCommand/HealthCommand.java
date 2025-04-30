package com.bit.mini.MyHealthCommand;

import java.util.List;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Component("healthCommand")
public class HealthCommand implements Command {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public void execute(Model model) {
		System.out.println("[HealthCommand]");
		
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		HttpSession session = request.getSession();
		HealthDao dao = sqlSession.getMapper(HealthDao.class);
		
		int userId = (int) session.getAttribute("userId");
		
		List<HealthDataDto> morningBefore = dao.getHealthData(userId, "아침", "전");
		List<HealthDataDto> morningAfter = dao.getHealthData(userId, "아침", "후");
		List<HealthDataDto> lunchBefore = dao.getHealthData(userId, "점심", "전");
		List<HealthDataDto> lunchAfter = dao.getHealthData(userId, "점심", "후");
		List<HealthDataDto> dinnerBefore = dao.getHealthData(userId, "저녁", "전");
		List<HealthDataDto> dinnerAfter = dao.getHealthData(userId, "저녁", "후");
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		
		model.addAttribute("morningBefore", gson.toJson(morningBefore));
		model.addAttribute("morningAfter", gson.toJson(morningAfter));
		model.addAttribute("lunchBefore", gson.toJson(lunchBefore));
		model.addAttribute("lunchAfter", gson.toJson(lunchAfter));
		model.addAttribute("dinnerBefore", gson.toJson(dinnerBefore));
		model.addAttribute("dinnerAfter", gson.toJson(dinnerAfter));
	}
}
