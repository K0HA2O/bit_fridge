package com.bit.mini.MyPageCommand;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.bit.mini.Controller.Command;
import com.bit.mini.dao.MyPageDao;
import com.bit.mini.dto.MyRecipeDto;

@Component("addMyRecipeCommand")
public class AddMyRecipeCommand implements Command {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public void execute(Model model) {

		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		HttpSession session = request.getSession();
		
		int user_id = (Integer) session.getAttribute("userId");
		String recipe_id = request.getParameter("recipe_id");
		String recipe_title = request.getParameter("recipe_title");
		String recipe_image = request.getParameter("recipe_image");
		
		MyRecipeDto dto = new MyRecipeDto();
		dto.setUser_id(user_id);
		dto.setRecipe_id(recipe_id);
		dto.setRecipe_title(recipe_title);
		dto.setRecipe_image(recipe_image);
		
		MyPageDao dao = sqlSession.getMapper(MyPageDao.class);
		dao.insertMyRecipe(dto);
	}
}
