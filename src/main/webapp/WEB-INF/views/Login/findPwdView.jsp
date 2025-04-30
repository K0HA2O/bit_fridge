<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>인증 코드 확인</title>
</head>
<form action="passwordReset" method="post">
    <input type="hidden" name="action" value="send" />
    <label>이메일:</label>
    <input type="email" name="email" required />
    <button type="submit">인증코드 발송</button>
</form>
<c:if test="${not empty message}">
    <p style="color: green;">${message}</p>
    <script>
        // 인증 코드 발송 성공 시 newPasswordView.jsp로 이동
        window.location.href = "newPasswordView.jsp?email=${email}";
    </script>
</c:if>
<c:if test="${not empty error}">
    <p style="color: red;">${error}</p>
</c:if>


</html>