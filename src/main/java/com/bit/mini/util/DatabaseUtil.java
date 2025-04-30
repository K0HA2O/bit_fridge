package com.bit.mini.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseUtil {
    public static Connection getConnection() throws Exception {
        String url = "jdbc:mysql://localhost:3306/project"; // DB URL
        String user = "root"; // 사용자 이름
        String password = "1234"; // 비밀번호
        Class.forName("com.mysql.cj.jdbc.Driver"); // 드라이버 로드
        return DriverManager.getConnection(url, user, password); // 연결 반환
    }
}