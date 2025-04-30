package com.bit.mini.MyPageCommand;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.bit.mini.Controller.Command;
import com.bit.mini.dao.MyPageDao;
import com.bit.mini.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component("infoModifyCommand")
public class InfoModifyCommand implements Command {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public void execute(Model model) {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		HttpSession session = request.getSession();
		ObjectMapper objectMapper = new ObjectMapper();
		
		int id = (Integer) session.getAttribute("userId");
		
		String username = request.getParameter("username");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String birthdateStr = request.getParameter("birthdate");
		String gender = request.getParameter("gender");
		String[] allergies = (String[]) request.getParameterValues("allergies");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date birthdate = null;
		try {
			java.util.Date utilDate = sdf.parse(birthdateStr);
			birthdate = new java.sql.Date(utilDate.getTime());
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}

		String allergiesJson = null;
		try {
			allergiesJson = objectMapper.writeValueAsString(Arrays.asList(allergies));
		} catch (Exception e) {
            System.err.println("JSON 변환 오류: " + e.getMessage());
            return;
        }
		
		MyPageDao dao = sqlSession.getMapper(MyPageDao.class);
		dao.modifyInfo(username, name, email, birthdate, UserDto.Gender.valueOf(gender.toUpperCase()), allergiesJson, id);
	}
}
