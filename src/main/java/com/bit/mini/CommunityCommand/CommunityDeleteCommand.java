package com.bit.mini.CommunityCommand;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.bit.mini.Controller.Command;
import com.bit.mini.dao.CommunityDao;
import com.bit.mini.dto.CommunityPostDto;

@Component("CommunityDeleteCommand")
public class CommunityDeleteCommand implements Command {

	@Autowired
	private CommunityDao dao;

	@Override
	public void execute(Model model) {

		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		
		int postId = Integer.parseInt(request.getParameter("id"));
		
		//세션에서 로그인된 사용자 ID 가져와서 확인
		HttpSession session = request.getSession();
		int loggedInUserId = (int) session.getAttribute("userId");
		
		//Dao로 게시글 작성자 정보 갖고와기
		CommunityPostDto post = dao.getPostDetail(postId);
		
		//작성자랑 로그인된 사용자 ID 비교하기
		if(post.getUserId() != loggedInUserId) {			
			model.addAttribute("errorMessage" , "삭제권한이 없습니다");
			throw new SecurityException("삭제 권한이 없습니다."); // 예외를 던지거나 에러 페이지로 이동
		}

		//권한 검증 통과 하면 삭제하기
		dao.deletePostById(postId); // posts 테이블 삭제
	}
}
