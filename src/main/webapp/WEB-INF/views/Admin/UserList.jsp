<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>회원 목록</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
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

.delete-btn {
	text-decoration: none;
	border: 1px solid #dc3545;
	border-radius: 20px;
	background-color: transparent;
	color: #dc3545;
	padding: 5px 15px;
	transition: background-color 0.3s ease;
}

.delete-btn:hover {
	background-color: #c82333; /* 호버 시 더 진한 빨간색 */
}

.btn {
	border-radius: 20px;
	padding: 5px 15px;
	background-color: transparent;
	transition: background-color 0.3s;
	vertical-align: middle; /* 텍스트 정렬 */
}
td {
    vertical-align: middle; /* 모든 td 내부 요소 중앙 정렬 */
}
</style>
</head>
<body class="p-4">

	<header>
      <nav class="navbar navbar-expand-lg mt-2 ms-4 me-4 pt-2 ps-4 pe-4">
         <div class="container-fluid mx-5">
            <a class="navbar-brand"
               href="./adminPage">냉장고를
               부탁해</a>
            <button class="navbar-toggler" type="button"
               data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown"
               aria-controls="navbarNavDropdown" aria-expanded="false"
               aria-label="Toggle navigation">
               <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse">
               <ul class="navbar-nav ms-auto gap-5" style="font-weight: bold;">
                  <li class="nav-item"><a class="nav-link" href="viewUserList">회원
                        관리</a></li>
                  <li class="nav-item"><a class="nav-link" href="manageAds">광고
                        관리</a></li>
                  <li class="nav-item"><a class="nav-link" href="./community">커뮤니티</a></li>
               </ul>
               <ul class="navbar-nav ms-auto">
                  <li class="nav-item"><a class="nav-link custom-button"
                     href="./logout">LOGOUT</a></li>
               </ul>
            </div>
         </div>
      </nav>
   </header>

	<div class="recipe-header m-2 p-5">
		<div>
			<span class="recipe-title">회원 목록</span> <span class="fresh-date">
				<span class="fresh-august-21-2025-span">Fresh</span> <span
				class="fresh-august-21-2025-span2">— August 21, 2025</span>
			</span>
		</div>
	</div>
	<main>
		<div class="container text-center mt-4">

		
						<table class="table table-hover">

								<tr>
									<th>ID</th>
									<th>이름</th>
									<th>이메일</th>
									<th>성별</th>
									<th></th>
									<th></th>
								</tr>


								<c:forEach var="user" items="${userList}">
									<tr>
										<td>${user.username}</td>
										<td>${user.name}</td>
										<td>${user.email}</td>
										<td>${user.gender}</td>
										<td><a href="viewUserDetail?id=${user.id}" class="btn btn-outline-secondary">상세 보기</a></td>
										<td>
											<!-- 삭제 버튼 --> <a href="deleteUser?username=${user.username}"
											onclick="return confirm('정말로 삭제하시겠습니까?');" class=" btn delete-btn">삭제</a>
										</td>
									</tr>
								</c:forEach>

		
						</table>
						
						
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