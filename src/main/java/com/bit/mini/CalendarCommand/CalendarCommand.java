package com.bit.mini.CalendarCommand;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.bit.mini.Controller.Command;
import com.bit.mini.dao.CalendarDao;
import com.google.gson.Gson;

@Component("calendarCommand")
public class CalendarCommand implements Command {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public void execute(Model model) {

		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		CalendarDao dao = sqlSession.getMapper(CalendarDao.class);
		HttpSession session = request.getSession();
		
		int user_id = (Integer) session.getAttribute("userId");
		List<Map<String, Object>> events = dao.getAllRecipeEvents(user_id);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (Map<String, Object> event : events) {
			Date date = (Date) event.get("date");
			String formattedDate = sdf.format(date);
			event.put("date", formattedDate);
		}
		
		String jsonEvents = new Gson().toJson(events);
		
		model.addAttribute("events", jsonEvents);
	}
}
