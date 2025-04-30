package com.bit.mini.UserCommand;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.bit.mini.Controller.Command;
import com.bit.mini.dao.UserDao;

@Component("userLoginCommand")
public class UserLoginCommand implements Command {
	
	@Autowired
    private UserDao dao;

	@Override
	public void execute(Model model) {
		
		Map<String, Object> map = model.asMap();
        HttpServletRequest request = (HttpServletRequest) map.get("request");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        boolean isLoginSuccess = dao.validateUser(username, password);

        if (isLoginSuccess) {
            boolean isAdmin = dao.checkIfAdmin(username);
            HttpSession session = request.getSession();
            int userId = dao.getUserIdByUsername(username);

            session.setAttribute("userId", userId);
            session.setAttribute("username", username);
            session.setAttribute("isAdmin", isAdmin);

            model.addAttribute("loginSuccess", true);
            model.addAttribute("isAdmin", isAdmin);
        } else {
            model.addAttribute("loginSuccess", false);
        }

	}

}
