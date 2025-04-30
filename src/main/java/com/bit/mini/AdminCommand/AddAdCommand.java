package com.bit.mini.AdminCommand;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.bit.mini.Controller.AdminCommand;
import com.bit.mini.dao.AdminDao;
import com.bit.mini.dto.AdDto;

@Component("AddAdCommand")
public class AddAdCommand implements AdminCommand {
	
	@Autowired
    private AdminDao dao;
	// 파일 저장 경로와 브라우저 접근 경로
    private String uploadDirectory = "C:\\Users\\seung\\Desktop\\files\\Spring\\Mini_Project2\\Bit_Fridge(ver11)\\src\\main\\webapp\\resources\\downloads";
    private String urlPath = "http://localhost:8080/mini/resources/downloads";

    @Override
   public void execute(HttpServletRequest request, HttpServletResponse response) {
        String linkUrl = request.getParameter("linkUrl");
        boolean isActive = "1".equals(request.getParameter("isActive"));

        MultipartFile imageFile = (MultipartFile) request.getAttribute("imagePath"); // 파일 가져오기
        String imagePath = null;

        try {
           if (imageFile != null && !imageFile.isEmpty()) {
              // 저장 경로 확인 및 생성
               File uploadDir = new File(uploadDirectory);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs(); // 디렉토리 생성
                }

               // 고유 파일명 생성 및 저장
                String fileName = imageFile.getOriginalFilename();
                File saveFile = new File(uploadDir, fileName);
                imageFile.transferTo(saveFile);

                // DB에 저장할 상대 경로 생성
                imagePath = urlPath + "/" + fileName;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 광고 데이터를 DB에 저장
        AdDto ad = new AdDto();
        ad.setImageUrl(imagePath);
        ad.setLinkUrl(linkUrl);
       ad.setActive(isActive);

        dao.addAd(ad); 
    }

}
