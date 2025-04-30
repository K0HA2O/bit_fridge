package com.bit.mini.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bit.mini.dto.UserDto;

@Repository
public class UserDao {
	
	@Autowired
    JdbcTemplate template;

	public boolean validateUser(String username, String inputPassword) {
        if (!checkUser(username)) {
            return false; 
        }
       
        return checkUserPassword(username, inputPassword);
    }
	
	public boolean checkUser(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        Integer count = template.queryForObject(sql, new Object[]{username}, Integer.class);
        return count != null && count > 0;
    }
	
	public boolean checkUserPassword(String username, String inputPassword) {
        String sql = "SELECT password FROM users WHERE username = ?";
        String storedPassword = template.queryForObject(sql, new Object[]{username}, String.class);
        System.out.println(storedPassword);
        return storedPassword != null && org.mindrot.jbcrypt.BCrypt.checkpw(inputPassword, storedPassword);
    }
	
	public boolean checkIfAdmin(String username) {
        String query = "SELECT is_admin FROM users WHERE username = ?";
        return template.queryForObject(query, new Object[]{username}, Boolean.class);
    }
	
	public int getUserIdByUsername(String username) {
        String sql = "SELECT id FROM users WHERE username = ?";
        return template.queryForObject(sql, new Object[]{username}, Integer.class);
    }
	
	public boolean signup(UserDto user) {
        String sql = "INSERT INTO users (username, password, email, name, birthdate, gender, health_info, allergies) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            int rows = template.update(sql, user.getUsername(), user.getPassword(), user.getEmail(), user.getName(),
                    user.getBirthdate(), user.getGender().name(), user.getHealthinfo(), user.getAllergies());
            return rows > 0;
        } catch (Exception e) {
        	System.out.println("[UserDao] : " + e.getMessage());
            return false;
        }
    }
	
	public boolean isEmailExists(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        return template.queryForObject(sql, Integer.class, email) > 0;
    }
	
	public void saveResetCode(String email, String resetCode) {
        String sql = "INSERT INTO reset_codes (email, reset_code, used) VALUES (?, ?, false)";
        template.update(sql, email, resetCode);
    }
	
	public String getLatestResetCode(String email) {
	    String sql = "SELECT reset_code FROM reset_codes WHERE email = ? AND used = false ORDER BY created_at DESC LIMIT 1";
	    try {
	    	System.out.println("sql 반환 값 : "+template.queryForObject(sql, String.class, email));
	        return template.queryForObject(sql, String.class, email);
	    } catch (EmptyResultDataAccessException e) {
	    	System.out.println("결과가 없습니다?");
	        return null; // 결과가 없을 경우 null 반환
	    }
	}
	
	public void updatePassword(String email, String password) {
        String sql = "UPDATE users SET password = ? WHERE email = ?";
        template.update(sql, password, email);
    }
	
	public String findId(String userEmail, String birthDate) {
        String sql = "SELECT username FROM users WHERE email = ? AND birthdate = ?";
        try {
            return template.queryForObject(sql, new Object[]{userEmail, birthDate}, String.class);
        } catch (Exception e) {
            System.err.println("占쏙옙占싱듸옙 찾占쏙옙 占쏙옙占쏙옙: " + e.getMessage());
            return null; // 占쏙옙占싱듸옙 찾占쏙옙 占쏙옙占쏙옙 占쏙옙占�
        }
    }
}
