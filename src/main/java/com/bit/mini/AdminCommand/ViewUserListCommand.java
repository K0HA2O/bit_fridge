package com.bit.mini.AdminCommand;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bit.mini.Controller.AdminCommand;
import com.bit.mini.dao.AdminDao;
import com.bit.mini.dto.UserDto;

@Component("ViewUserListCommand")
public class ViewUserListCommand implements AdminCommand{

	
	@Autowired
    private AdminDao dao;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
    	
        List<UserDto> userList = dao.getUserList();

        request.setAttribute("userList", userList);
    }
    
    
}
