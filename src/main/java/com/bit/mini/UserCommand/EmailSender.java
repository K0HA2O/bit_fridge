package com.bit.mini.UserCommand;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class EmailSender {

	private JavaMailSender mailSender;
    
	    public EmailSender() {
	    }

	    public EmailSender(JavaMailSender mailSender) {
	        this.mailSender = mailSender;
	    }
	    
	 // Setter 占쌨쇽옙占쏙옙 (XML占쏙옙占쏙옙 占쏙옙占쏙옙 占쏙옙占쏙옙占싹듸옙占쏙옙 占쌥듸옙占� 占십울옙)
	    public void setMailSender(JavaMailSender mailSender) {
	        this.mailSender = mailSender;
	    }
	    
	    

	    /**
	     * 占싱몌옙占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙 占쌨쇽옙占쏙옙
	     * @param recipient 占쏙옙占쏙옙占쏙옙 占싱몌옙占쏙옙
	     * @param subject 占싱몌옙占쏙옙 占쏙옙占쏙옙
	     * @param text 占싱몌옙占쏙옙 占쏙옙占쏙옙
	     */
	    public void sendEmail(String recipient, String subject, String text) {
	        try {
	            MimeMessage message = mailSender.createMimeMessage();
	            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

	            // 占싱몌옙占쏙옙 占쏙옙占쏙옙
	            helper.setFrom("goodcarefridge@gmail.com"); // 占쌩쏙옙占쏙옙 占싱몌옙占쏙옙
	            helper.setTo(recipient); // 占쏙옙占쏙옙占쏙옙 占싱몌옙占쏙옙
	            helper.setSubject(subject); // 占싱몌옙占쏙옙 占쏙옙占쏙옙
	            helper.setText(text, true); // 占싱몌옙占쏙옙 占쏙옙占쏙옙 (HTML 占쏙옙占쏙옙)

	            // 占싱몌옙占쏙옙 占쌩쇽옙
	            mailSender.send(message);

	            // 占쏙옙占쏙옙占� 占싸깍옙
	            System.out.println("占싱몌옙占쏙옙 占쌩쇽옙 占쏙옙占쏙옙: " + recipient);
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new RuntimeException("占싱몌옙占쏙옙 占쌩쇽옙 占쏙옙 占쏙옙占쏙옙 占쌩삼옙: " + e.getMessage());
	        }
	    }
}
