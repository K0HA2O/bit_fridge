package com.bit.mini.UserCommand;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.bit.mini.Controller.Command;
import com.bit.mini.dao.UserDao;

@Component("passwordResetCommand")
public class PasswordResetCommand implements Command {
	
	@Autowired
    private UserDao dao;

    @Autowired
    private EmailSender emailSender;
    
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    private String generateResetCode() {
        return String.format("%06d", new Random().nextInt(1000000)); // 6�ڸ� ���� ���� �ڵ� ����
    }

	@Override
	public void execute(Model model) {

		HttpServletRequest request = (HttpServletRequest) model.asMap().get("request");
        String action = request.getParameter("action");
        String email = request.getParameter("email");
        if ("send".equals(action)) {
            sendResetCode(email, model);
        } else if ("verify".equals(action)) {
            verifyResetCode(email, request, model);
        } else if ("reset".equals(action)) {
            resetPassword(email, request, model);
        }
	}

	private void sendResetCode(String email, Model model) {
        if (!dao.isEmailExists(email)) {
            model.addAttribute("error", "입력하신 이메일은 등록되지 않았습니다.");
            return;
        }

        String resetCode = generateResetCode();
        dao.saveResetCode(email, resetCode);

        try {
            emailSender.sendEmail(email, "비밀번호 재설정 인증코드", "인증코드: " + resetCode);
             model.addAttribute("message", "인증코드가 이메일로 전송되었습니다.");
             model.addAttribute("email", email); // 다음 단계에 이메일을 전달
         } catch (Exception e) {
             model.addAttribute("error", "인증코드 전송 중 문제가 발생했습니다.");
         }
    }
	
	private void verifyResetCode(String email, HttpServletRequest request, Model model) {
        String inputCode = request.getParameter("resetCode");
        System.out.println("input code : "+inputCode);
        String savedCode = dao.getLatestResetCode(email);

        if (savedCode != null && savedCode.equals(inputCode)) {
        	request.getSession().setAttribute("verified", true); // 인증 성공 시 세션 저장
            model.addAttribute("message", "인증코드가 확인되었습니다. 새 비밀번호를 입력하세요.");
            model.addAttribute("email", email); // 비밀번호 재설정 페이지로 전달
        } else {
            model.addAttribute("error", "인증코드가 유효하지 않습니다.");
        }
    }
	
	private void resetPassword(String email, HttpServletRequest request, Model model) {
        String newPassword = request.getParameter("newPassword");

        if (newPassword == null || newPassword.isEmpty()) {
        	model.addAttribute("error", "비밀번호는 반드시 입력해야 합니다.");
            return;
        }

        String encryptedPassword = passwordEncoder.encode(newPassword);

        dao.updatePassword(email, encryptedPassword);
        model.addAttribute("message", "비밀번호가 성공적으로 재설정되었습니다.");
        
        // 비밀번호 재설정 후 세션 초기화
        request.getSession().removeAttribute("verified");
        request.getSession().removeAttribute("email");
    }
}
