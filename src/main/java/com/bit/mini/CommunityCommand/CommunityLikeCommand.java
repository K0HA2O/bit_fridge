package com.bit.mini.CommunityCommand;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.bit.mini.Controller.Command;
import com.bit.mini.dao.CommunityDao;

@Component("CommunityLikeCommand")
public class CommunityLikeCommand implements Command {

	@Autowired
    private CommunityDao dao;

	@Override
    public void execute(Model model) {
		System.out.println("CommunityLikeCommand 실행됨");
        // Model에서 파라미터 가져오기
        Map<String, Object> map = model.asMap();
        int postId = (int) map.get("postId");
        int userId = (int) map.get("userId");
        
        
        System.out.println("CommunityLikeCommand received postId: " + postId);
        

         // 사용자가 이미 좋아요를 눌렀는지 확인
        boolean userLiked = dao.hasUserLiked(postId, userId);
        
        
        if (userLiked) {
            // 이미 좋아요를 눌렀다면 좋아요 취소
            dao.decreaseLike(postId, userId);
        } else {
            // 좋아요 추가
            dao.increaseLike(postId, userId);
        }



        // 좋아요 수 갱신
        int updatedLikeCount = dao.getLikeCount(postId);
        System.out.println("Updated like count for postId " + postId + ": " + updatedLikeCount);

        // Model에 갱신된 좋아요 수 저장
        model.addAttribute("likeCount", updatedLikeCount);
    }
    
   
}
