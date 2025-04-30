package com.bit.mini.AdminCommand;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bit.mini.Controller.AdminCommand;
import com.bit.mini.dao.AdminDao;
import com.bit.mini.dto.AdDto;

@Component("ManageAdCommand")
public class ManageAdCommand implements AdminCommand {
	
	@Autowired
	private AdminDao dao;
	
	
	@Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 모든 광고 가져오기
            List<AdDto> ads = dao.getAds(false); 
            System.out.println("불러온 광고 개수: " + ads.size());
            for (AdDto ad : ads) {
                System.out.println("광고 ID: " + ad.getId());
                System.out.println("이미지 URL: " + ad.getImageUrl());
                System.out.println("링크 URL: " + ad.getLinkUrl());
            }
            request.setAttribute("ads", ads); // JSP에 전달
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "광고 데이터를 불러오는 중 오류가 발생했습니다.");
        }
    }

}
