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

@Component("CommunityWriteCommand")
public class CommunityWriteCommand implements Command {

    @Autowired
    private CommunityDao dao;
    
    

    private String uploadDirectory = "C:\\Users\\seung\\Desktop\\files\\Spring\\Mini_Project2\\Bit_Fridge(ver11)\\src\\main\\webapp\\resources\\downloads";  // 실제 파일 저장 경로
    private String urlPath = "http://localhost:8080/mini/resources/downloads"; // 브라우저 접근 경로

    
        @Override
        public void execute(Model model) {
            // 요청에서 파라미터 가져오기
            Map<String, Object> map = model.asMap();
            HttpServletRequest request = (HttpServletRequest) map.get("request");
            
            String title = request.getParameter("title");
            String content = request.getParameter("content");
           
            MultipartFile imageFile = (MultipartFile) map.get("imagePath"); // 업로드된 파일 받기
            String imagePath = null;
            
            
            try {
                if (imageFile != null && !imageFile.isEmpty()) {
                    // 서버에 저장될 경로 설정
                    File uploadDir = new File(uploadDirectory);
                    if (!uploadDir.exists()) {
                    	System.out.println("존재 안함");
                        uploadDir.mkdirs(); // 디렉토리 없으면 생성
                    }
                    
                    System.out.println("path1: " +uploadDir.getAbsolutePath());

                    // 고유 파일명 생성 (UUID 사용 가능)
                    String fileName = imageFile.getOriginalFilename();
                    File saveFile = new File(uploadDir, fileName);
                    System.out.println("path2: " + saveFile.getAbsolutePath());
                    imageFile.transferTo(saveFile); // 파일 저장

                    // DB에 저장할 상대 경로 생성
                    imagePath = urlPath + "/" + fileName; // URL 경로로 저장
                    
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            
            // 세션에서 로그인된 사용자 ID 가져오기
            HttpSession session = request.getSession();
            int userId = (int) session.getAttribute("userId");

            // DAO 호출하여 데이터 저장
            dao.insertPost(title, content, userId, imagePath);
        }
    }


