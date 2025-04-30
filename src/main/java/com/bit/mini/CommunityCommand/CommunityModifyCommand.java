package com.bit.mini.CommunityCommand;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.bit.mini.Controller.Command;
import com.bit.mini.dao.CommunityDao;
import com.bit.mini.dto.CommunityPostDto;



@Component("CommunityModifyCommand")
public class CommunityModifyCommand implements Command {

	
	@Autowired
    private CommunityDao dao;
	
	
	private String uploadDirectory = "C:\\Users\\seung\\Desktop\\files\\Spring\\Mini_Project2\\Bit_Fridge(ver11)\\src\\main\\webapp\\resources\\downloads"; // 실제 저장 경로
    private String urlPath = "http://localhost:8080/mini/resources/downloads"; // 브라우저 접근 경로
	
	

    @Override
    public void execute(Model model) {
        
        Map<String, Object> map = model.asMap();
        HttpServletRequest request = (HttpServletRequest) map.get("request");
        MultipartFile imageFile = (MultipartFile) map.get("imagePath");
        
        
        int postId = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        
        String imagePath = null;
        
        
        try {
            // 파일 업로드 처리
            if (imageFile != null && !imageFile.isEmpty()) {
                File uploadDir = new File(uploadDirectory);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                String fileName = imageFile.getOriginalFilename();
                File saveFile = new File(uploadDir, fileName);
                imageFile.transferTo(saveFile);

                // DB에 저장할 URL 경로 생성
                imagePath = urlPath + "/" + fileName;
            } else {
                // 파일 업로드 없을 시 기존 이미지 경로 유지
                CommunityPostDto post = dao.getPostDetail(postId);
                imagePath = post.getImagePath();
            }
            
            
            
        
         // 로그인된 사용자와 게시글 작성자 비교
            HttpSession session = request.getSession();
            int loggedInUserId = (int) session.getAttribute("userId");
            CommunityPostDto post = dao.getPostDetail(postId);
            if (post.getUserId() != loggedInUserId) {
                throw new SecurityException("수정 권한이 없습니다.");
            }

            // DAO 호출로 게시글 수정
            dao.updatePost(postId, title, content, imagePath);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("파일 업로드 중 오류가 발생했습니다.", e);
        }
    }
}

