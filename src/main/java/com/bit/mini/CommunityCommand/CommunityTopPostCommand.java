package com.bit.mini.CommunityCommand;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.bit.mini.Controller.Command;
import com.bit.mini.dao.AdminDao;
import com.bit.mini.dao.CommunityDao;
import com.bit.mini.dto.AdDto;
import com.bit.mini.dto.CommunityPostDto;
import com.bit.mini.dto.UserDto;


@Component("CommunityTopPostCommand")
public class CommunityTopPostCommand implements Command {

	
	@Autowired
    private CommunityDao dao;
	
	@Autowired
    private AdminDao adao;

    @Override
    public void execute(Model model) {
        
    	List<CommunityPostDto> topPosts = dao.getTopThreePosts();
        model.addAttribute("topPosts", topPosts);
        
        // 占쏙옙占쏙옙 占쏙옙占쏙옙 占쌉시깍옙占쏙옙 占쌜쇽옙占쏙옙 占쏙옙占쏙옙占� 占쏙옙占쏙옙占쏙옙占쏙옙
        UserDto mostActiveUser = dao.getMostActiveUser();
        
        model.addAttribute("mostActiveUser", mostActiveUser);
        
        
        AdDto randomAd = null;
        try {
            List<AdDto> activeAds = adao.getActiveAds(); // 활占쏙옙화占쏙옙 占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙占쏙옙
            if (!activeAds.isEmpty()) {
                Random random = new Random();
                randomAd = activeAds.get(random.nextInt(activeAds.size())); // 占쏙옙占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙 占쏙옙占쏙옙
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        
        model.addAttribute("randomAd", randomAd);
    }
       
    

}
