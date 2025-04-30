<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>음성 인식 결과</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/navbar.css">
<style>
.container {
	margin-top: 20px;
}

table {
	width: 100%;
	border-collapse: collapse;
}

th, td {
	text-align: center;
	padding: 12px;
	border: 1px solid #ddd;
}

thead {
	background-color: #23983e;
	color: white;
}

tbody tr:nth-child(even) {
	background-color: #f9f9f9;
}

.form-control {
	text-align: center;
}

.save-btn {
	margin-top: 15px;
	background-color: #28a745;
	color: white;
	border: none;
	padding: 10px 20px;
	border-radius: 5px;
	cursor: pointer;
	transition: background-color 0.3s;
}

.save-btn:hover {
	background-color: #00721b;
}
</style>
<script
	src="${pageContext.request.contextPath}/resources/js/dateHandler.js"></script>
</head>

<body>

	<header>
		<nav class="navbar navbar-expand-lg m-4 p-4">
			<div class="container-fluid mx-5">
				<a class="navbar-brand"
					href="http://localhost:8080/mini/health?loginSuccess=true&isAdmin=false">냉장고를
					부탁해</a>
				<button class="navbar-toggler" type="button"
					data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown"
					aria-controls="navbarNavDropdown" aria-expanded="false"
					aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>
				<div class="collapse navbar-collapse">
					<ul class="navbar-nav ms-auto gap-5" style="font-weight: bold;">
						<li class="nav-item"><a class="nav-link"
							href="http://localhost:8080/mini/fridge">My 냉장고</a></li>
						<li class="nav-item"><a class="nav-link"
							href="http://localhost:8080/mini/recipe">My 레시피</a></li>
						<li class="nav-item"><a class="nav-link"
							href="http://localhost:8080/mini/health">My 건강</a></li>
						<li class="nav-item"><a class="nav-link"
							href="http://localhost:8080/mini/calendar">My 캘린더</a></li>
						<li class="nav-item"><a class="nav-link"
							href="http://localhost:8080/mini/shopping">온라인 쇼핑</a></li>
						<li class="nav-item"><a class="nav-link"
							href="http://localhost:8080/mini/community">커뮤니티</a></li>
					</ul>
					<ul class="navbar-nav ms-auto">
						<li class="nav-item"><a class="nav-link custom-button"
							href="#">MyPage</a></li>
					</ul>
				</div>
			</div>
		</nav>
	</header>

	<div class="recipe-header m-2 p-5">
		<div>
			<span class="recipe-title">My 냉장고</span> <span class="fresh-date">
				<span class="Today"></span> <span class="date"></span>
			</span>
		</div>
	</div>

	<main>

	<div class="container" style="width: 80%;">
		<h1 class="text-left mb-4">음성 인식 결과</h1>

		<form
			action="${pageContext.request.contextPath}/fridge/saveVoiceResult"
			method="post">
			<table>
				<thead>
					<tr>
						<th>선택</th>
						<th>재료명 (수정 가능)</th>
						<th style="width: 10%">수량</th>
						<th style="width: 10%">단위</th>
						<th>유효기간</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty parsedIngredients}">
						<c:forEach var="ingredient" items="${parsedIngredients}"
							varStatus="status">
							<tr>
								<td><input type="checkbox" name="selectedIndices"
									value="${status.index}"></td>
								<td><input type="text" class="form-control"
									name="name_${status.index}" value="${ingredient.name}"></td>
								<td><input type="number" class="form-control"
									name="quantity_${status.index}" value="${ingredient.quantity}"
									step="0.1"></td>
								<td><input type="text" class="form-control"
									name="unit_${status.index}" value="${ingredient.unit}"></td>
								<td><input type="date" class="form-control"
									name="expirationDate_${status.index}"></td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${empty parsedIngredients}">
						<tr>
							<td colspan="5">음성 데이터가 없습니다.</td>
						</tr>
					</c:if>
				</tbody>
			</table>

			<div class="text-end mt-3">
				<button type="submit" class="save-btn" style="width: 10%">저장</button>
			</div>
		</form>
	</div>
</main>
</body>
</html>
