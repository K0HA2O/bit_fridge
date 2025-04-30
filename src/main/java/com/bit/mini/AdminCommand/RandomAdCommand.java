package com.bit.mini.AdminCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bit.mini.Controller.AdminCommand;
import com.bit.mini.dao.AdminDao;
import com.bit.mini.dto.AdDto;


@Component("RandomAdCommand")
public class RandomAdCommand implements AdminCommand {
    

	@Autowired
	private AdminDao dao;
	
	
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            AdDto randomAd = dao.getRandomAd(); // 랜덤 광고 가져오기
            request.setAttribute("randomAd", randomAd);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "랜덤 광고를 가져오는 중 오류가 발생했습니다.");
        }
    }
}