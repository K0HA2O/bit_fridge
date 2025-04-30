package com.bit.mini.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public interface AdminCommand {

	void execute(HttpServletRequest request, HttpServletResponse response);
	
	
	 // 새로운 메서드 추가 (디폴트 메서드로 정의)
    default Object fetchData(HttpServletRequest request, HttpServletResponse response) {
        return null; 
    }
}
