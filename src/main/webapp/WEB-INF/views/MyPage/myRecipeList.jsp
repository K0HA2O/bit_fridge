<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>찜한 레시피 목록</title>
</head>
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
</style>
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
			<span class="recipe-title">My 레시피</span> <span class="fresh-date">
				<span class="fresh-august-21-2025-span">Fresh</span> <span
				class="fresh-august-21-2025-span2">— August 21, 2025</span>
			</span>
		</div>
	</div>
	
	<main>
		<div class="container text-center mt-4">
			<div style="overflow-x: hidden; overflow-y: auto;">
				<div class="row row-cols-1 row-cols-md-4 g-4" style="width: 80rem;">

					<c:if test="${not empty myRecipeList}">

						<!-- recommendedRecipes 리스트를 반복하여 레시피 출력 -->
						<c:forEach var="recipe" items="${myRecipeList}">
							<div class="col">
								<div class="card h-100">

									<img src="${recipe.recipe_image}" alt="Recipe Image" width="230px" class="card-img-top" style="object-fit: cover;" />
									<!-- 레시피 이미지 출력 -->
									<div class="card-body">
										<a href="../recipe/recipeInfo?id=${recipe.recipe_id}" class="card-title">${recipe.recipe_title}</a><br><br>
										
										<form action="../myPage/deleteMyRecipe" method="post">
											<input type="hidden" name="id" value="${recipe.id}">
											<button type="submit" class="delete-btn">삭제</button>
										</form>
									</div>
									<!-- 레시피 제목 출력 -->
								</div>
							</div>
						</c:forEach>

					</c:if>

					<c:if test="${empty myRecipeList}">
						<p>저장된 레시피가 없습니다.</p>
					</c:if>

				</div>
			</div>
		</div><br>
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