<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>새 비밀번호 입력</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
            background-size: cover;
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            background-image: url('./resources/img/image0.png');
            background-size: contain;
            background-repeat: no-repeat;
            background-position: center;
        }
        a {
            text-decoration: none;
        }
    </style>
</head>
<body>

<div class="container d-flex justify-content-center align-items-center min-vh-100">
    <div class="card shadow-sm rounded-3" style="width: 400px;">
        <div class="card-body">
            <h5 class="card-title text-center mb-4">새 비밀번호 입력</h5>

            <!-- 인증 코드 입력 폼 -->
            <c:if test="${empty sessionScope.verified}">
                <form action="passwordReset" method="post">
                    <input type="hidden" name="action" value="verify" />
                    
                    <div class="mb-3">
                        <label class="form-label">인증코드:</label>
                        <input type="text" name="resetCode" class="form-control" required />
                    </div>

                    <input type="hidden" name="email" value="${email}" />

                    <div class="text-center">
                        <button type="submit" class="btn btn-primary w-100">인증코드 확인</button>
                    </div>
                </form>
            </c:if>

            <!-- 비밀번호 재설정 폼 -->
            <c:if test="${not empty sessionScope.verified}">
                <form action="passwordReset" method="post">
                    <input type="hidden" name="action" value="reset" />

                    <div class="mb-3">
                        <label class="form-label">새 비밀번호:</label>
                        <input type="password" name="newPassword" class="form-control" required />
                    </div>

                    <input type="hidden" name="email" value="${email}" />

                    <div class="text-center">
                        <button type="submit" class="btn btn-success w-100">비밀번호 재설정</button>
                    </div>
                </form>
            </c:if>

            <!-- 오류 및 메시지 표시 -->
            <c:if test="${not empty error}">
                <p class="text-danger text-center mt-3">${error}</p>
            </c:if>

            <c:if test="${not empty message}">
                <p class="text-success text-center mt-3">${message}</p>
            </c:if>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
