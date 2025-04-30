<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내정보</title>
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
	width: 80%; /* 원하는 크기로 조정 */
	margin: auto; /* 중앙 정렬 */
}

.listbox {
	width: 60%;
}

.listbox-item {
	display: flex;
	align-items: center;
	justify-content: space-between; /* 두 개의 체크박스가 일정한 간격을 유지하도록 정렬 */
	width: 100%; /* 가로 너비를 동일하게 맞춤 */
}

.listbox-item label {
	flex: 1; /* 라벨과 체크박스를 동일한 간격으로 배치 */
	text-align: left; /* 왼쪽 정렬 */
}

.listbox-item input[type="checkbox"] {
	margin-right: 5px; /* 체크박스 오른쪽 여백 추가 */
}
</style>
</head>
<body class="p-4">

	<header>
		<nav class="navbar navbar-expand-lg m-2 p-2">
			<div class="container-fluid mx-5">
				<a class="navbar-brand" href="#">냉장고를 부탁해</a>
				<button class="navbar-toggler" type="button"
					data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown"
					aria-controls="navbarNavDropdown" aria-expanded="false"
					aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>
				<div class="collapse navbar-collapse">
					<ul class="navbar-nav ms-auto gap-5" style="font-weight: bold;">
						<li class="nav-item"><a class="nav-link" href="../fridge">My
								냉장고</a></li>
						<li class="nav-item"><a class="nav-link" href="../recipe">My
								레시피</a></li>
						<li class="nav-item"><a class="nav-link" href="../health">My
								건강</a></li>
						<li class="nav-item"><a class="nav-link" href="../calendar">My
								캘린더</a></li>
						<li class="nav-item"><a class="nav-link" href="#">온라인 쇼핑</a></li>
						<li class="nav-item"><a class="nav-link" href="../community">커뮤니티</a></li>
					</ul>
					<ul class="navbar-nav ms-auto">
						<li class="nav-item"><a class="nav-link custom-button"
							href="../myPage">MyPage</a></li>
					</ul>
				</div>
			</div>
		</nav>
	</header>

	<div class="recipe-header m-2 p-5">
		<div>
			<span class="recipe-title">MyInfo</span> <span
				class="fresh-august-21-2025-span">Fresh</span> <span
				class="fresh-august-21-2025-span2">— August 21, 2025</span>
		</div>
	</div>

	<!-- 무조건 <main> 태그 안에 자신이 구현할 것 넣으면 됩니다!!!! -->
	<main>
		<div class="container text-center mt-4">
			<div>

				<form action="../myPage/infoModify" method="post">
					<table class="table table-borderless">
						<tr>
							<th class="text-start p-2">아이디</th>
							<td><input name="username" type="text" class="form-control"
								value="${myInfo.username}" readonly></td>
						</tr>
						<tr>
							<th class="text-start p-2">이름</th>
							<td><input name="name" type="text" class="form-control"
								value="${myInfo.name}"></td>
						</tr>
						<tr>
							<th class="text-start p-2">이메일</th>
							<td><input name="email" type="email" class="form-control"
								value="${myInfo.email}"></td>
						</tr>
						<tr>
							<th class="text-start p-2">생년월일</th>
							<td><input name="birthdate" type="date" class="form-control"
								value="${myInfo.birthdate}"></td>
						</tr>
						<tr>
							<th class="text-start p-2">성별</th>
							<td><select name="gender" class="form-select">
									<option value="MALE">남성</option>
									<option value="FEMALE">여성</option>
							</select></td>
						</tr>
						<tr>
							<th class="text-start p-2">알레르기</th>
							<td><c:set var="allergyList" value="${allergyList}" />
								<div class="listbox border rounded p-2">
									<div class="listbox-item">
										<label> <input type="checkbox" id="dairy"
											name="allergies" value="dairy"
											<c:if test="${fn:contains(allergyList, 'dairy')}">checked="checked"</c:if>>
											유제품
										</label> <label> <input type="checkbox" id="egg"
											name="allergies" value="egg"
											<c:if test="${fn:contains(allergyList, 'egg')}">checked="checked"</c:if>>
											달걀
										</label>
									</div>

									<div class="listbox-item">
										<label> <input type="checkbox" id="gluten"
											name="allergies" value="gluten"
											<c:if test="${fn:contains(allergyList, 'gluten')}">checked="checked"</c:if>>
											글루텐
										</label> <label> <input type="checkbox" id="grain"
											name="allergies" value="grain"
											<c:if test="${fn:contains(allergyList, 'grain')}">checked="checked"</c:if>>
											곡물
										</label>
									</div>

									<div class="listbox-item">
										<label> <input type="checkbox" id="Peanut"
											name="allergies" value="Peanut"
											<c:if test="${fn:contains(allergyList, 'Peanut')}">checked="checked"</c:if>>
											땅콩
										</label> <label> <input type="checkbox" id="Seafood"
											name="allergies" value="Seafood"
											<c:if test="${fn:contains(allergyList, 'Seafood')}">checked="checked"</c:if>>
											해산물
										</label>
									</div>

									<div class="listbox-item">
										<label> <input type="checkbox" id="Sesame"
											name="allergies" value="Sesame"
											<c:if test="${fn:contains(allergyList, 'Sesame')}">checked="checked"</c:if>>
											참깨
										</label> <label> <input type="checkbox" id="Shellfish"
											name="allergies" value="Shellfish"
											<c:if test="${fn:contains(allergyList, 'Shellfish')}">checked="checked"</c:if>>
											갑각류
										</label>
									</div>

									<div class="listbox-item">
										<label> <input type="checkbox" id="Soy"
											name="allergies" value="Soy"
											<c:if test="${fn:contains(allergyList, 'Soy')}">checked="checked"</c:if>>
											콩
										</label> <label> <input type="checkbox" id="Sulfite"
											name="allergies" value="Sulfite"
											<c:if test="${fn:contains(allergyList, 'Sulfite')}">checked="checked"</c:if>>
											황산염
										</label>
									</div>

									<div class="listbox-item">
										<label> <input type="checkbox" id="TreeNut"
											name="allergies" value="TreeNut"
											<c:if test="${fn:contains(allergyList, 'TreeNut')}">checked="checked"</c:if>>
											견과류
										</label> <label> <input type="checkbox" id="Wheat"
											name="allergies" value="Wheat"
											<c:if test="${fn:contains(allergyList, 'Wheat')}">checked="checked"</c:if>>
											밀
										</label>
									</div>
								</div></td>
						</tr>
						<tr>
							<td colspan="2" class="text-center p-3"><input type="submit"
								value="수정" class="recipe-button btn btn-outline-success"></td>
						</tr>
					</table>

				</form>

			</div>
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