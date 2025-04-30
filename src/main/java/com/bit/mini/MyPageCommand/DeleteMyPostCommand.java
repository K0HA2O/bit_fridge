package com.bit.mini.MyPageCommand;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.bit.mini.Controller.Command;
import com.bit.mini.dao.MyPageDao;

@Component("deleteMyPostCommand")
public class DeleteMyPostCommand implements Command {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public void execute(Model model) {

		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		MyPageDao dao = sqlSession.getMapper(MyPageDao.class);
		
		String id = request.getParameter("id");
		dao.deleteMyPost(id);
	}
}
