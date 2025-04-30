package com.bit.mini.AdminCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bit.mini.Controller.AdminCommand;
import com.bit.mini.dao.AdminDao;


@Component("PostDeleteCommand")
public class PostDeleteCommand implements AdminCommand {
	
	@Autowired
	private AdminDao dao;
	
	
	 @Override
	    public void execute(HttpServletRequest request, HttpServletResponse response) {
	       
	        String postId = request.getParameter("id");
	      
	        dao.deletePost(postId);
	    }
	 
	 
	  

}
