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

@Component("updateCommand")
public class UpdateCommand implements Command {
	
	@Autowired
    private SqlSession sqlSession;

	@Override
	public void execute(Model model) {
		Map<String, Object> map = model.asMap();
        HttpServletRequest request = (HttpServletRequest) map.get("request");
        HttpSession session = request.getSession();
        
        int user_id = (Integer) session.getAttribute("userId");
        int event_id = Integer.parseInt(request.getParameter("id"));
        String event_date = request.getParameter("event_date");
        
        System.out.println(event_id);

        CalendarDao dao = sqlSession.getMapper(CalendarDao.class);
        dao.updateEventDate(user_id, event_id, event_date);

	}

}
