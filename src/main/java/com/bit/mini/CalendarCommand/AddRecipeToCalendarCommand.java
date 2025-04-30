package com.bit.mini.CalendarCommand;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.bit.mini.Controller.Command;
import com.bit.mini.dao.CalendarDao;

@Component("addRecipeToCalendarCommand")
public class AddRecipeToCalendarCommand implements Command {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public void execute(Model model) {
		
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		HttpSession session = request.getSession();
		
		String recipe_id = request.getParameter("id");
		String recipe_title = request.getParameter("title");
		int user_id = (Integer) session.getAttribute("userId");
		String event_date = request.getParameter("date");
		
		System.out.println("id : " + recipe_id);
		System.out.println("recipe_title : " +recipe_title);
		System.out.println("user_id : " + user_id);
		System.out.println("date : " + event_date);
		
		CalendarDao dao = sqlSession.getMapper(CalendarDao.class);
		dao.addRecipeToCalendar(recipe_id, recipe_title, user_id, event_date);
	}
}
