package com.bit.mini.CommunityCommand;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.bit.mini.Controller.Command;
import com.bit.mini.dao.CommunityDao;
import com.bit.mini.dto.CommunityPostDto;



// Community.jsp占쏙옙占쏙옙 占쏙옙占쏙옙 회占쏙옙(占쌉시깍옙 占쏙옙占썲많占쏙옙 占쌜쇽옙占쏙옙 占쏙옙占�) 占싱몌옙 클占쏙옙 占쏙옙 占쌔댐옙 회占쏙옙占쏙옙 占쌉시깍옙 占쏙옙占쏙옙占� 占쏙옙占쏙옙占쌍댐옙 占쏙옙占�
@Component("CommunityUserPostCommand")
public class CommunityUserPostCommand implements Command {

	@Autowired
	private CommunityDao dao;
	
	@Override
    public void execute(Model model) {
		
		// Model占쏙옙占쏙옙 占쏙옙占쏙옙占� 占싱몌옙 占쏙옙占쏙옙占쏙옙占쏙옙
        String username = (String) model.asMap().get("username");
        

     // `username`占쏙옙 占쏙옙占쏙옙 占쏙옙占� 占쏙옙占쏙옙 처占쏙옙
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("占쏙옙占쏙옙占� 占싱몌옙占쏙옙 占쏙옙효占쏙옙占쏙옙 占십쏙옙占싹댐옙.");
        }

        // DAO 호占쏙옙 占쏙옙 占쏙옙占� 처占쏙옙
        List<CommunityPostDto> userPosts = dao.getPostsByUser(username);
        
        String name = userPosts.isEmpty() ? "占쏙옙 占쏙옙 占쏙옙占쏙옙" : userPosts.get(0).getAuthor();

        // 占쏙옙占쏙옙占� Model占쏙옙 占쌩곤옙
        model.addAttribute("userPosts", userPosts);
        model.addAttribute("name", name);
    }
	
	
}
