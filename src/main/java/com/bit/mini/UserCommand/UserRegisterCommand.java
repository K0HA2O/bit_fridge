package com.bit.mini.UserCommand;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.bit.mini.Controller.Command;
import com.bit.mini.dao.UserDao;
import com.bit.mini.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component("userRegisterCommand")
public class UserRegisterCommand implements Command {
	
	@Autowired
    private UserDao dao;

	@Override
	public void execute(Model model) {

		System.out.println("registerCommand에 들어왔다");
	       
        Map<String, Object> map = model.asMap();
        String username = (String) map.get("username");
        String password = (String) map.get("password");
        String email = (String) map.get("email");
        String name = (String) map.get("name");
        System.out.println(name);
        String birthdate = (String) map.get("birthdate");
        String gender = (String) map.get("gender");
        String healthinfo = (String) map.get("healthinfo");
        String[] allergies = (String[]) map.get("allergies");

        System.out.println("registerCommand에서 String에 담기");
        
        Map<String, String> errors = new HashMap<String, String>();

        if (!isValidUsername(username)) {
            errors.put("username", "아이디는 4~10자의 영어 또는 영어+숫자 조합이어야 합니다.");
        }
        if (!isValidPassword(password)) {
            errors.put("password", "비밀번호는 8자 이상의 영어, 숫자, 특수문자 조합이어야 합니다.");
        }
        if (!isValidEmail(email)) {
            errors.put("email", "올바른 이메일 형식을 입력하세요.");
        }
        if (!isValidName(name)) {
            errors.put("name", "이름은 한글로 2자 이상 입력해야 합니다.");
        }

        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            model.addAttribute("username", username);
            model.addAttribute("email", email);
            model.addAttribute("name", name);
            model.addAttribute("birthdate", birthdate);
            model.addAttribute("gender", gender);
            model.addAttribute("healthinfo", healthinfo);
            model.addAttribute("allergies", allergies);
            return;
        }

        System.out.println("healthinfo 값: " + healthinfo);

        
        
        UserDto user = new UserDto();
        user.setUsername(username);
        user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        user.setEmail(email);
        user.setName(name);
        user.setBirthdate(java.sql.Date.valueOf(birthdate));
        user.setGender(UserDto.Gender.valueOf(gender.toUpperCase()));

        System.out.println("healthinfo 값: " + healthinfo);
        
     // JSON 변환
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String allergiesJson = objectMapper.writeValueAsString(Arrays.asList(allergies)); // JSON 배열로 변환
            user.setAllergies(allergiesJson);
        } catch (Exception e) {
            System.err.println("JSON 변환 오류: " + e.getMessage());
            model.addAttribute("error", "JSON 변환 중 오류가 발생했습니다.");
            return;
        }
        
        
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String healthinfoJson = objectMapper.writeValueAsString(Arrays.asList(healthinfo)); // JSON 배열로 변환
            user.setHealthinfo(healthinfoJson);
        } catch (Exception e) {
            System.err.println("JSON 변환 오류: " + e.getMessage());
            model.addAttribute("error", "JSON 변환 중 오류가 발생했습니다.");
            return;
        }

        
        
        System.out.println("회원가입 데이터 확인: " + user);

        System.out.println("DB 저장 전 healthinfo 값: " + user.getHealthinfo());

        boolean isRegistered = dao.signup(user);
        
        System.out.println("회원가입 결과: " + isRegistered);

        if (isRegistered) {
            model.addAttribute("success", true); // 회원가입 성공 여부 추가
        } else {
            model.addAttribute("success", false); // 회원가입 실패 처리
            model.addAttribute("error", "회원가입에 실패했습니다. 다시 시도해주세요.");
        }
	}
	
	private boolean isValidUsername(String username) {
        return username != null && username.matches("^[a-zA-Z][a-zA-Z0-9]{3,11}$");
    }

    private boolean isValidPassword(String password) {
        return password != null && password.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+]).{8,}$");
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }

    private boolean isValidName(String name) {
        return name != null && name.matches("^[가-힣]{2,}$");
    }
}
