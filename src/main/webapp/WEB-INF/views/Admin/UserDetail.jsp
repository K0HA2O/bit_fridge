<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>회원 상세 정보</title>
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

.listbox {
	width: 200px;
	border: 1px solid #ccc;
	padding: 10px;
	overflow-y: auto;
	max-height: 150px; /* 스크롤 가능 */
}

.listbox-item {
	display: flex;
	align-items: center;
	margin-bottom: 5px;
}

.listbox-item label {
	margin-left: 5px;
	cursor: pointer;
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
			<span class="recipe-title">회원 정보</span> <span class="fresh-date">
				<span class="fresh-august-21-2025-span">Fresh</span> <span
				class="fresh-august-21-2025-span2">— August 21, 2025</span>
			</span>
		</div>
	</div>
	<main>
		<div class="container text-center mt-4">
		
			<c:if test="${not empty user}">
				<table class="table table-borderless">
					<tr>
						<th class="text-start p-2">아이디</th>
						<td class="form-control">${user.username}</td>
					</tr>
					<tr>
						<th class="text-start p-2">이름</th>
						<td class="form-control">${user.name}</td>
					</tr>
					<tr>
						<th class="text-start p-2">이메일</th>
						<td class="form-control">${user.email}</td>
					</tr>
					<tr>
						<th class="text-start p-2">성별</th>
						<td class="form-control">${user.gender}</td>
					</tr>
					<tr>
						<th class="text-start p-2">생년월일</th>
						<td class="form-control">${user.birthdate}</td>
					</tr>
				</table><br>
			</c:if>
			<c:if test="${empty user}">
				<p style="color: red; text-align: center;">해당 사용자를 찾을 수 없습니다.</p>
			</c:if>
			
			<a href="viewUserList" class="recipe-button">회원 목록으로 돌아가기</a><br><br>


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