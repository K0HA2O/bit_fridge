<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>게시글 상세보기</title>
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

.btn {
	border-radius: 20px;
	padding: 5px 15px;
	background-color: transparent;
	transition: background-color 0.3s;
	margin-right: 15px;
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

.list-btn {
	text-decoration: none;
	border: 1px solid #dc3545;
	border-radius: 20px;
	background-color: transparent;
	color: #dc3545;
	padding: 5px 15px;
	transition: background-color 0.3s ease;
}

.list-btn:hover {
	background-color: #c82333; /* 호버 시 더 진한 빨간색 */
}

.table tr:not(:last-child) {
	border-bottom: 15px solid transparent; /* 행 사이 간격 추가 */
}
.table tr {
    display: flex;
    align-items: center;
}

.table th {
    width: 200px; /* 원하는 크기로 조정 */
    text-align: left;
}

.table td {
    flex: 1;
}
.table {
    width: 80%;  /* 원하는 크기로 조정 */
    margin: auto; /* 중앙 정렬 */
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
			<span class="recipe-title">게시글 상세보기</span> <span class="fresh-date">
				<span class="Today"></span> <span class="date"></span>
			</span> </span>
		</div>
	</div>
<main>
	<div class="container text-center mt-4">

		<table class="table table-borderless">
			<tr>
				<th class="text-start p-2">제목</th>
				<td class="form-control">${post.title}</td>
			</tr>
			<tr>
				<th class="text-start p-2">내용</th>
				<td class="form-control">${post.content}</td>
			</tr>
			<tr>
				<th class="text-start p-2">이미지</th>
				<td class="form-control"><c:choose>
						<c:when test="${not empty post.imagePath}">
							<img src="${post.imagePath}" alt="이미지" width="200">
						</c:when>
						<c:otherwise>
							<p>이미지가 없습니다</p>
						</c:otherwise>
					</c:choose></td>
			</tr>
			<tr>
				<th class="text-start p-2">작성자</th>
				<td class="form-control">${post.author}</td>
			</tr>
			<tr>
				<th class="text-start p-2">작성일</th>
				<td class="form-control"><fmt:formatDate
						value="${post.created_at}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
			<tr>
				<th class="text-start p-2">조회수</th>
				<td class="form-control">${post.hit}</td>
			</tr>

			<tr>
				<th class="text-start p-2">좋아요 수</th>
				<td class="form-control">${post.likeCount}</td>
			</tr>
		</table>

		<div class="d-flex gap-2 justify-content-center mt-4">
			<form action="./ContentlikePost" method="post">
				<!--  <input type="hidden" name="postId" value="${postId}" /> -->
				<input type="hidden" name="postId" value="${post.id}" /> <input
					type="hidden" name="userId" value="${sessionScope.userId}" />
				<button type="submit" class="btn btn-outline-success">좋아요</button>
			</form>
			<a href="list" class="btn btn-outline-secondary">목록으로</a>

			<c:if test="${post.userId == sessionScope.userId}">
				<a href="modify?id=${post.id}" class="btn btn-outline-primary">수정</a>
				<a href="delete?id=${post.id}" class="btn btn-outline-danger">삭제</a>
			</c:if>

		</div>
		<br>

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
</footer> <script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
	crossorigin="anonymous"></script>
</body>
</html>