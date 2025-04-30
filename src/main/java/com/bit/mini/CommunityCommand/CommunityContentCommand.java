package com.bit.mini.CommunityCommand;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.bit.mini.Controller.Command;
import com.bit.mini.dao.CommunityDao;
import com.bit.mini.dto.CommunityPostDto;

@Component("CommunityContentCommand")
public class CommunityContentCommand implements Command {

	@Autowired
	private CommunityDao dao;

	@Override
	public void execute(Model model) {

		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		String idParam = request.getParameter("id");     //CommunityList.jsp에서 게시글 목록에서 상세정보 볼때 
		String postIdParam = request.getParameter("postId");   //Community.jsp 상위 3개 게시글 상세정보 볼때
		
		int postId = (idParam != null) ? Integer.parseInt(idParam) : Integer.parseInt(postIdParam);

        // DAO 메서드 선택: `id` 또는 `postId`에 따라 다른 메서드 호출
        CommunityPostDto post;
        if (idParam != null) {
            
            dao.upHit(postId); 
            post = dao.getPostDetail(postId);
        } else {
            
            post = dao.clickContent(postId);
        }

        
        model.addAttribute("post", post);
    }
}