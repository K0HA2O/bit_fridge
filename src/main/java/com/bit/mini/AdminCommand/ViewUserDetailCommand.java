package com.bit.mini.AdminCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bit.mini.Controller.AdminCommand;
import com.bit.mini.dao.AdminDao;
import com.bit.mini.dto.UserDto;

@Component("ViewUserDetailCommand")
public class ViewUserDetailCommand implements AdminCommand {

	
	@Autowired
    private AdminDao dao;
    
	@Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String idParam = request.getParameter("id");

        // 디버깅: 요청 파라미터 확인
        System.out.println("요청 파라미터 ID: " + idParam);

        if (idParam == null || idParam.isEmpty()) {
            System.err.println("요청 파라미터 'id'가 비어 있습니다.");
            request.setAttribute("error", "잘못된 요청입니다. ID가 필요합니다.");
            return;
        }

        int userId;
        try {
            userId = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            System.err.println("ID 파라미터가 유효한 숫자가 아닙니다: " + idParam);
            request.setAttribute("error", "잘못된 ID 형식입니다.");
            return;
        }

        System.out.println("정상적으로 파싱된 userId: " + userId);

        UserDto user = dao.getUserById(userId);
        if (user == null) {
            System.err.println("ID " + userId + "에 해당하는 사용자가 존재하지 않습니다.");
            request.setAttribute("error", "해당 사용자가 존재하지 않습니다.");
        } else {
            request.setAttribute("user", user);
        }
    }
}
