package com.bit.mini.CommunityCommand;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.bit.mini.Controller.Command;
import com.bit.mini.dao.CommunityDao;
import com.bit.mini.dto.CommunityPostDto;

@Component("CommunityListCommand")
public class CommunityListCommand implements Command {

    @Autowired
    private CommunityDao dao;

    @Override
    public void execute(Model model) {
        HttpServletRequest request = (HttpServletRequest) model.asMap().get("request");
        
        
        //요청된 페이지 번호를 가져온다. 기본값 1
        int page = 1;
        String pageParam = request.getParameter("page");      
        if(pageParam != null) {
        	page = Integer.parseInt(pageParam);
        }

        //페이지당 게시글 수와 전체 게시글 개수를 가져옵니다.
        int size = 10; 
        int totalPosts = dao.getTotalPostCount(); 
        int totalPages = (int)Math.ceil((double) totalPosts / size);
        
        
        // 현재 페이지 번호가 유효 범위를 벗어나면 보정한다
        if (page < 1 ) page = 1;
        if(page > totalPages) page = totalPages;
        
        
        // 현재 페이지에 해당하는 게시글 목록을 가져옵니다.
        List<CommunityPostDto> topPosts = dao.getTopPosts(page, size);
        if(topPosts == null) {
        	topPosts = new ArrayList<>();
        }

        model.addAttribute("currentPage", page);   // 현재 페이지 번호
        model.addAttribute("totalPages", totalPages);  // 전체 페이지 수 
        model.addAttribute("topPosts", topPosts);      // 게시글 목록
    }

}
