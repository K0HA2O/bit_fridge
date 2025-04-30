<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 목록</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
	<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/navbar.css">
	<!-- 시간 js -->
<script
	src="${pageContext.request.contextPath}/resources/js/dateHandler.js"></script>
<style>
.navbar-brand {
	color: #23983e;
	/* 브랜드 텍스트 색상 변경 */
	font-weight: bold;
}

.nav-link {
	color: #000000;
	/* 링크 텍스트 색상 변경 */
}

.recipe-header {
	display: flex;
	align-items: center;
	justify-content: space-between;
	padding: 10px 20px;
	background-color: #fff;
}

.recipe-title {
	font-size: 2em;
	font-weight: bold;
	margin: 0;
}

.fresh-date {
	font-size: 1em;
	color: #6c757d;
	margin-left: 10px;
}

.recipe-buttons {
	display: flex;
	gap: 10px;
}

.recipe-button {
	border: 1px solid #23983e;
	border-radius: 20px;
	padding: 5px 15px;
	background-color: transparent;
	color: #23983e;
	transition: background-color 0.3s;
    text-decoration: none;
}

.recipe-button:hover {
	background-color: #28a745;
}

.custom-button {
	background-color: #23983e;
	color: white;
	border-radius: 10px;
	padding: 5px 15px;
	text-decoration: none;
	transition: background-color 0.3s, color 0.3s;
}

.custom-button:hover {
	background-color: #00721b;
	color: white;
}

.community-btn {
	text-decoration: none;
	border: 1px solid #dc3545;
	border-radius: 20px;
	background-color: transparent;
	color: #dc3545;
	padding: 5px 15px;
	transition: background-color 0.3s ease;
    position: absolute;
    right: 0;
    margin-bottom: 10;
}

.community-btn:hover {
	background-color: #c82333; /* 호버 시 더 진한 빨간색 */
}
.table a{
    text-decoration: none;
}

.pagination a {
    text-decoration: none;
}

.pagination {
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 12px;
    margin-top: 20px;
    position: relative;
}
</style>
</head>
<body class="p-4">

	<header>
		<nav class="navbar navbar-expand-lg mt-2 ms-4 me-4 pt-2 ps-4 pe-4">
			<div class="container-fluid mx-5">
				<a class="navbar-brand"
					href="<%=(Boolean) session.getAttribute("isAdmin") ? "./adminPage"
		: "http://localhost:8080/mini/health?loginSuccess=true&isAdmin=false"%>">
					냉장고를 부탁해 </a>
				<button class="navbar-toggler" type="button"
					data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown"
					aria-controls="navbarNavDropdown" aria-expanded="false"
					aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>
				<div class="collapse navbar-collapse">
					<ul class="navbar-nav ms-auto gap-5" style="font-weight: bold;">
						<%
						if ((Boolean) session.getAttribute("isAdmin")) {
						%>
						<li class="nav-item"><a class="nav-link" href="viewUserList">회원
								관리</a></li>
						<li class="nav-item"><a class="nav-link" href="manageAds">광고
								관리</a></li>
						<li class="nav-item"><a class="nav-link" href="./community">커뮤니티</a></li>
						<%
						} else {
						%>
						<li class="nav-item"><a class="nav-link"
							href="http://localhost:8080/mini/fridge/loading">My 냉장고</a></li>
						<li class="nav-item"><a class="nav-link"
							href="http://localhost:8080/mini/recipe/loading2">My 레시피</a></li>
						<li class="nav-item"><a class="nav-link"
							href="http://localhost:8080/mini/health">My 건강</a></li>
						<li class="nav-item"><a class="nav-link"
							href="http://localhost:8080/mini/calendar">My 캘린더</a></li>
						<li class="nav-item"><a class="nav-link"
							href="http://localhost:8080/mini/shopping">온라인 쇼핑</a></li>
						<li class="nav-item"><a class="nav-link"
							href="http://localhost:8080/mini/help">도움말</a></li>
						<li class="nav-item"><a class="nav-link"
							href="http://localhost:8080/mini/community">커뮤니티</a></li>
						<%
						}
						%>
					</ul>
					<ul class="navbar-nav ms-auto">
						<li class="nav-item"><a class="nav-link custom-button"
							href="<%=(Boolean) session.getAttribute("isAdmin") ? "./logout" : "http://localhost:8080/mini/myPage"%>">
								<%=(Boolean) session.getAttribute("isAdmin") ? "LOGOUT" : "MyPage"%>
						</a></li>
					</ul>
				</div>
			</div>
		</nav>
	</header>
	<div class="recipe-header m-2 p-5">
		<div>
			<span class="recipe-title">${username}님의 게시글</span> <span class="fresh-date">
				<span class="Today"></span> <span class="date"></span>
			</span> </span>
		</div>
	</div>
	
	<main>
		<div class="container text-center mt-4">
            <c:choose>
                <c:when test="${not empty userPosts}">

                    <table class="table table-hover">

                        <tr>
                            <th>제목</th>
                            <th>작성일</th>
                        </tr>

                        <c:forEach var="post" items="${userPosts}">
                            <tr>
                                <td><a href="./showPost?id=${post.id}">${post.title}</a></td>
                                <td>${post.created_at}</td>
                            </tr>
                        </c:forEach>

                    </table>

                </c:when>

                <c:otherwise>
                    <p>게시글이 없습니다.</p>
                </c:otherwise>
            </c:choose>

            <a href="community" class="recipe-button">커뮤니티로 돌아가기</a><br><br>
		</div>
	</main>
	
	<footer class="bg-light py-1">
		<div class="container text-center">
			<p>&copy; 2025 레시피 관리 사이트. All rights reserved.</p>
			<ul class="list-inline">
				<li class="list-inline-item"><a href="#">이용 약관</a></li>
				<li class="list-inline-item"><a href="#">개인정보 보호정책</a></li>
			</ul>
			<div class="social-media">
				<a href="#">Facebook</a> | <a href="#">Twitter</a> | <a href="#">Instagram</a>
			</div>
		</div>
	</footer>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
		crossorigin="anonymous"></script>
</body>
</html>