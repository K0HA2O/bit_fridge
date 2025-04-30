package com.bit.mini.MyPageCommand;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.bit.mini.Controller.Command;
import com.bit.mini.dao.MyPageDao;
import com.bit.mini.dto.CommunityPostDto;

@Component("myPostListCommand")
public class MyPostListCommand implements Command {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public void execute(Model model) {

		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		HttpSession session = request.getSession();
		MyPageDao dao = sqlSession.getMapper(MyPageDao.class);
		
		int user_id = (Integer) session.getAttribute("userId");
		List<CommunityPostDto> posts = dao.getMyPost(user_id);
		
		model.addAttribute("posts", posts);
	}
}
