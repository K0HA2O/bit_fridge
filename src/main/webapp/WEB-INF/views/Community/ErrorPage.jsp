<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>오류</title>
</head>
<body>
    <h2>오류가 발생했습니다.</h2>
    <p style="color: red;">${errorMessage}</p>
    <a href="list">목록으로 돌아가기</a>
</body>
</html>