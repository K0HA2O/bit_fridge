package com.bit.mini.Controller;

import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
	
	@Inject
	@Qualifier("userLoginCommand")
	private Command userLoginCommand;
	
	@Inject
	@Qualifier("userRegisterCommand")
	private Command userRegisterCommand;
	
	@Inject
    @Qualifier("findIdCommand")
    private Command findIdCommand;

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home() {
		return "home";
	}
	
	@Inject
    @Qualifier("passwordResetCommand")
    private Command passwordResetCommand;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, Model model) {
        model.addAttribute("request", request);
        
        
        userLoginCommand.execute(model); 
        
       
        Boolean loginSuccess = (Boolean) model.asMap().get("loginSuccess");
        Boolean isAdmin = (Boolean) model.asMap().get("isAdmin");

        if (loginSuccess != null && loginSuccess) {
            // 관리자와 일반 사용자에 따라 뷰 이동
            if (isAdmin != null && isAdmin) {
                return "redirect:/adminPage"; // 관리자 페이지로 이동
            } else {
                return "redirect:./health"; // 일반 사용자 메인 페이지로 이동
            }
        } else {
            model.addAttribute("error", "아이디나 비밀번호를 확인해주세요!");
            return "home"; // 로그인 실패 시 로그인 페이지로 이동
        }
    }
	
	@RequestMapping(value = "/register_view", method = RequestMethod.GET)
	public String registerView() {
		System.out.println("registerView()");
	    return "Login/register"; 
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(HttpServletRequest request, Model model) {
		
		System.out.println("register()");
		
        model.addAttribute("username", request.getParameter("username"));
        model.addAttribute("password", request.getParameter("password"));
        model.addAttribute("email", request.getParameter("email"));
        model.addAttribute("name", request.getParameter("name"));
        model.addAttribute("birthdate", request.getParameter("birthdate"));
        model.addAttribute("gender", request.getParameter("gender"));
        model.addAttribute("healthinfo", request.getParameter("healthinfo"));
        model.addAttribute("allergies", request.getParameterValues("allergies"));
        
        userRegisterCommand.execute(model);
        
        Map<String, String> errors = (Map<String, String>) model.asMap().get("errors");
        if (errors != null && !errors.isEmpty()) {
            model.addAllAttributes(errors);
            return "Login/register";
        }

        Boolean isRegistered = (Boolean) model.asMap().get("success");
        if (isRegistered != null && isRegistered) {
            return "redirect:/home"; 
        } else {
            model.addAttribute("error", "?��?��?��?��?��?��?��?��?��?��?�� ?��?��?��?��?��?��?��?��?��?��?��?��?��?��. ?��?��?��?�� ?��?��?��?��?��?��?��?��?��?��?��?��?��?��.");
            return "Login/register";
        }
    }
	
	@RequestMapping(value = "/findPwdView", method = RequestMethod.GET)
    public String findPwdView() {
        return "Login/findPwdView"; 
    }
	
	@RequestMapping(value = "/passwordReset", method = RequestMethod.POST)
    public String handlePasswordReset(HttpServletRequest request, Model model) {
        model.addAttribute("request", request);
        passwordResetCommand.execute(model);

        String action = request.getParameter("action");
        if ("send".equals(action)) {
            if (model.asMap().get("error") == null) {
                return "Login/newPasswordView";
            } else {
                return "Login/findPwdView";
            }
        } else if ("verify".equals(action)) {
            if (model.asMap().get("error") == null) {
                return "Login/newPasswordView";
            } else {
                return "Login/findPwdView";
            }
        } else if ("reset".equals(action)) {
            if (model.asMap().get("error") == null) {
                return "redirect:/home";
            } else {
                return "Login/newPasswordView";
            }
        }
        return "error";
    }
	
	@RequestMapping(value = "/findIdView", method = RequestMethod.GET)
    public String findIdView() {
        return "Login/findIdView"; 
    }
	
	@RequestMapping(value = "/findId", method = RequestMethod.POST)
    public String findId(HttpServletRequest request, Model model) {
    	
        model.addAttribute("email", request.getParameter("email")); 
        model.addAttribute("birthdate", request.getParameter("birthdate")); 

        findIdCommand.execute(model);

        System.out.println("findIdCommand ?��?��?��?��?��곤옙 ?��?��?��?�� ?��?��?��?��?��?��뤄옙?��?��?�� ?��?��?��?��?��?��");
        
        String username = (String) model.asMap().get("username");
        if (username != null) {
            request.getSession().setAttribute("username", username);
            return "Login/findIdResultView"; // ?��?��?��?��?��?��?�� 찾占?��?�� ?��?��?��?��?��?�� ?��?��?��?���? ?��?��?��?��?��?��?��?��?��
        } else {
            model.addAttribute("error", "?��?��?��?��?��?��?�� 찾占?��?�� ?��?��?�� ?��?��?��?��?��?��?��?��?��?��. ?��?��?��?�� ?��?��?��?��?��?��?��?��?��?��?��?��?��?��.");
            return "Login/findIdView"; // ?��?��?��?��?��?�� ?��?��?�� ?��?��?��?�� ?��?��?�� ?��?��?��?��?��?��?��?��?��?��?��?�� ?��?��?��?��
        }
    }
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {

		session.invalidate();

		return "redirect:/login";
	}
	
	@RequestMapping(value = "/help", method = RequestMethod.GET)
	   public String helping() {
	      
	      return "Help/Help";
	}
}
