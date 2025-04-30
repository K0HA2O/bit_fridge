<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>커뮤니티</title>
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

.community-container {
	padding: 20px;
	flex: 1;
}

.card {
	border: 1px solid #ddd;
	padding: 15px;
	text-align: center;
}

.card h2 {
	color: #23983e;
	/* 브랜드 텍스트 색상 변경 */
	font-weight: bold;
}

.card h3 {
	font-weight: bold;
}

.card h4 {
	margin: 10px 0;
}

.ad-banner {
	text-align: center;
	width: 100%;
}

.ad-banner a {
	color: #007bff;
	text-decoration: none;
	font-weight: bold;
}

.ad-banner {
	text-align: center;
	margin-top: 10px;
}

.ad-banner img {
	max-width: 60px;
	height: auto;
}

table {
	margin: 0 auto;
	width: 80%; /* 필요에 따라 조절 */
}

.active-member-card {
	height: 695px; /* 원하는 높이로 조절 */
	min-height: 250px;
	justify-content: center;
}

.recipe-img {
	width: 400px; /* 원하는 너비 설정 */
	height: 300px; /* 원하는 높이 설정 */
	/* object-fit: cover; /* 비율을 유지하며 영역을 채움 */ */
	border-radius: 10px; /* 둥근 모서리 (선택 사항) */
	display: block; /* 가운데 정렬을 위한 block 설정 */
	margin: 0 auto; /* 수평 가운데 정렬 */
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
			<span class="recipe-title">커뮤니티</span> <span class="fresh-date">
				<span class="Today"></span> <span class="date"></span>
			</span>
		</div>
		<div class="recipe-buttons">
			<button type="button" class="recipe-button btn btn-outline-success"
				onClick="location.href='./list';">게시판으로 이동</button>
		</div>
	</div>
	<main>
		<div class="container text-center mt-4">
			<div class="community-container">

				<table class="table align-middle text-center">
					<tr>
						<td colspan="2">
							<div class="card">
								<h2>이달의 레시피</h2>
								<c:if test="${not empty topPosts[0]}">
									<img src="${topPosts[0].imagePath}" alt="레시피 이미지"
										class="recipe-img">
									<h4>
										<a href="./showPost?postId=${topPosts[0].id}"
											style="text-decoration: none;"> ${topPosts[0].title} </a>
									</h4>
									<p>Made by ${topPosts[0].author}</p>
									<div>${topPosts[0].likeCount}좋아요</div>
								</c:if>
							</div>
						</td>
						<td colspan="2" rowspan="2">
							<div class="card active-member-card">
								<h2>이달의 열심 회원</h2>
								<c:choose>
									<c:when test='${mostActiveUser.gender eq "MALE"}'>
										<img src="http://localhost:8080/mini/resources/img/Male.png"
											alt="남성 회원"
											style="width: 300px; height: 300px; border-radius: 50%;" class="recipe-img">
									</c:when>
									<c:when test='${mostActiveUser.gender eq "FEMALE"}'>
										<img src="http://localhost:8080/mini/resources/img/Female.png"
											alt="여성 회원"
											style="width: 300px; height: 300px; border-radius: 50%;" class="recipe-img">
									</c:when>
								</c:choose>
								<c:if test="${not empty mostActiveUser}">
									<h4>
										<a href="./userPosts?username=${mostActiveUser.username}&${mostActiveUser.name}"
											style="text-decoration: none;"> ${mostActiveUser.username} 님 </a>
									</h4>
								</c:if>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div class="card">
								<h3>2nd</h3>
								<c:if test="${not empty topPosts[1]}">
									<h4>
										<a href="./showPost?postId=${topPosts[1].id}"
											style="text-decoration: none;"> ${topPosts[1].title} </a>
									</h4>
									<p>Made by ${topPosts[1].author}</p>
									<div>${topPosts[1].likeCount}좋아요</div>
								</c:if>
							</div>
						</td>
						<td>
							<div class="card">
								<h3>3rd</h3>
								<c:if test="${not empty topPosts[2]}">
									<h4>
										<a href="./showPost?postId=${topPosts[2].id}"
											style="text-decoration: none;"> ${topPosts[2].title} </a>
									</h4>
									<p>Made by ${topPosts[2].author}</p>
									<div>${topPosts[2].likeCount}좋아요</div>
								</c:if>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</main>
	<c:if test="${not empty randomAd && randomAd.isActive}">
		<a href="${randomAd.linkUrl}" target="_blank"> <img
			src="${randomAd.imageUrl}" alt="광고 이미지" class="ad-banner">
		</a>
	</c:if>
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