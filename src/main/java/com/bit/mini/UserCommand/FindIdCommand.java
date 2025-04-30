package com.bit.mini.UserCommand;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.bit.mini.Controller.Command;
import com.bit.mini.dao.UserDao;

@Component("findIdCommand")
public class FindIdCommand implements Command {
	
	@Autowired
    private UserDao dao;

	@Override
	public void execute(Model model) {

		Map<String, Object> map = model.asMap();
        String email = (String) map.get("email");
        String birthdate = (String) map.get("birthdate");

        System.out.println("FindIdCommand: email = " + email);
        System.out.println("FindIdCommand: birthdate = " + birthdate);

        
        String username = dao.findId(email, birthdate);

        if (username != null) {
            model.addAttribute("username", username);
            model.addAttribute("success", true);
        } else {
            model.addAttribute("success", false);
            model.addAttribute("error", "에러입니다.");
        }
	}
}
