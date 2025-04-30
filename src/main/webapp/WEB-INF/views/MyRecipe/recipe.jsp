<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My 레시피</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
      integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/navbar.css">
<style>
.card-title {
    color: black;
    text-decoration: none; /* 기본적으로 밑줄 제거 */
    transition: color 0.3s ease, background-color 0.3s ease;
}

.card-title:hover {
   text-decoration: underline; /* 커서 올릴 시에 언더라인 */
    padding: 5px 10px;
    border-radius: 5px; /* 둥근 모서리 */
     font-weight: bold; /* 글씨 굵게 변경 */
}

</style>
</head>
<body class="p-4">

	<header>
<nav class="navbar navbar-expand-lg mt-2 ms-4 me-4 pt-2 ps-4 pe-4">
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
					</ul>
					<ul class="navbar-nav ms-auto">
						<li class="nav-item"><a class="nav-link custom-button"
							href="http://localhost:8080/mini/myPage">MyPage</a></li>
					</ul>
				</div>
			</div>
		</nav>
	</header>

	<div class="recipe-header m-2 p-5">
		<div>
        <span class="recipe-title">My 레시피</span> <span class="fresh-date">
        <span class="Today"></span> 
        <span class="date"></span>
      </span>
      </div>

	</div>

	<main>
		<div class="container text-center mt-4">
			<div style="overflow-x: hidden; overflow-y: auto;">
				<div class="row row-cols-1 row-cols-md-4 g-4" style="width: 80rem;">

					<c:if test="${not empty recommendedRecipes}">

						<!-- recommendedRecipes 리스트를 반복하여 레시피 출력 -->
						<c:forEach var="recipe" items="${recommendedRecipes}">
							<div class="col">
								<div class="card h-100">


									<img src="${recipe.image}" alt="Recipe Image" width="230px" class="card-img-top" style="object-fit: cover;" />
									<!-- 레시피 이미지 출력 -->
									<div class="card-body">
										<a href="./recipe/recipeInfo?id=${recipe.id}" class="card-title">${recipe.title}</a><br><br>
										
										<form action="./myPage/addMyRecipe" method="post">
											<input type="hidden" name="recipe_id" value="${recipe.id}">
											<input type="hidden" name="recipe_image" value="${recipe.image}">
											<input type="hidden" name="recipe_title" value="${recipe.title}">
											<button type="submit" class="custon-button btn btn-outline-success">MY레시피에 등록</button>
										</form>
									</div>
									<!-- 레시피 제목 출력 -->

								</div>
							</div>
						</c:forEach>

					</c:if>

					<c:if test="${empty recommendedRecipes}">
						<p>추천 레시피가 없습니다.</p>
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
		<script src="${pageContext.request.contextPath}/resources/js/dateHandler.js"></script>
</body>
</html>