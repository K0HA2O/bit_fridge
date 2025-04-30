<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>냉장고 재료 목록</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/navbar.css">
<!-- 시간 js -->
<script
	src="${pageContext.request.contextPath}/resources/js/dateHandler.js"></script>
<style>
/* 배경 설정 */
body {
	background:
		url('${pageContext.request.contextPath}/resources/img/fridge.jpg')
		no-repeat center center fixed;
	background-size: cover;
	background-color: rgba(255, 255, 255, 0.5); /* 배경 투명도 조정 */
	background-blend-mode: overlay; /* 배경 이미지와 색상 혼합 */
}
.recipe-header {
    background-color: rgb(255 255 255 / 0%);
}

/* 냉장고 내부 배경 투명도 적용 */
.container {
	margin-top: 20px;
	padding: 20px;
	background: rgba(255, 255, 255, 0.5); /* 반투명 효과 */
	border-radius: 10px;
	backdrop-filter: blur(8px); /* 흐림 효과 */
}

.card {
	border-radius: 10px;
	box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);
	padding: 15px;
	text-align: center;
	background: rgba(255, 255, 255, 0.8); /* 개별 카드 투명도 조정 */
}

.card img {
	width: 100px;
	height: 100px;
	object-fit: cover;
	margin: auto;
}

.expiration-date {
	font-weight: bold;
}

.red-text {
	color: red;
}

.yellow-text {
	color: orange;
}

.green-text {
	color: green;
}

.delete-btn {
	margin-top: 10px;
	background-color: #dc3545;
	color: white;
	border: none;
	padding: 5px 10px;
	border-radius: 5px;
	transition: background-color 0.3s ease;
}

.delete-btn:hover {
	background-color: #c82333;
}

.add-btn {
	background-color: #23983e;
	color: white;
	border-radius: 5px;
	padding: 8px 15px;
	text-decoration: none;
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
			<span class="recipe-title">My 냉장고</span> <span class="fresh-date">
				<span class="Today"></span> <span class="date"></span>
			</span>
		</div>
	</div>
	<main>
		<div class="container">
			<!-- 제목과 추가 버튼 -->
			<div class="d-flex justify-content-between align-items-center mb-3">
				<h1>냉장고 재료 목록</h1>
				<a href="./addition" class="btn custom-button">추가</a>
			</div>

			<div class="row">
				<c:forEach var="ingredient" items="${ingredients}">
					<div class="col-md-3 mb-4">
						<div class="card">
							<!-- 재료 이미지 -->
							<c:choose>
								<c:when test="${not empty ingredient.image_url}">
									<img src="${ingredient.image_url}"
										alt="${ingredient.ingredient_name}">
								</c:when>
								<c:otherwise>
									<img src="/resources/images/default-image.jpg" alt="기본 이미지">
								</c:otherwise>
							</c:choose>

							<!-- 재료 정보 -->
							<h5 class="mt-2">${ingredient.ingredient_name}</h5>
							<p>수량: ${ingredient.quantity} ${ingredient.unit}</p>

							<!-- 유효기간 표시 -->
							<p
								class="expiration-date 
              <c:choose>
                <c:when test="${ingredient.daysLeft <= 3}"> red-text </c:when>
                <c:when test="${ingredient.daysLeft <= 7}"> yellow-text </c:when>
                <c:otherwise> green-text </c:otherwise>
              </c:choose>">
								유효기간: ${ingredient.expiration_date}</p>

							<!-- 삭제 버튼 -->
							<form action="./delete" method="post">
								<input type="hidden" name="ingredient_id"
									value="${ingredient.ingredient_id}">
								<button type="submit" class="delete-btn">삭제</button>
							</form>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</main>
	<script>
document.addEventListener("DOMContentLoaded", function () {
    let expiredIngredients = [];

    <c:forEach var="ingredient" items="${ingredients}">
        var expirationDateStr = "${ingredient.expiration_date}".trim();
        if (expirationDateStr) { 
            var expirationDate = new Date(expirationDateStr.replace(/-/g, "/"));
            var today = new Date();
            today.setHours(0, 0, 0, 0);
            expirationDate.setHours(0, 0, 0, 0);

            if (!isNaN(expirationDate.getTime()) && expirationDate < today) {
                expiredIngredients.push("${ingredient.ingredient_name} (유효기간: ${ingredient.expiration_date})");
            }
        }
    </c:forEach>

    if (expiredIngredients.length > 0) {
        let message = "⚠️ 유효기간이 지난 재료가 있습니다!\n\n" + expiredIngredients.join("\n");
        setTimeout(() => alert(message), 500);
    }
});
</script>

</body>
</html>
