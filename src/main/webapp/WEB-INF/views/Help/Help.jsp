<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>도움말</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/navbar.css">
	<!-- 시간 js -->
<script
	src="${pageContext.request.contextPath}/resources/js/dateHandler.js"></script>
    <style>
        .help-container {
            max-width: 1000px;
            margin: 50px auto;
            padding: 20px;
        }
        .help-title {
            font-size: 26px;
            font-weight: bold;
            color: #23983e;
        }
        .help-card {
            border: none;
            border-radius: 12px;
            overflow: hidden;
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }
        .help-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
        }
        .help-img {
    width: 100%;
    height: 200px; /* 원하는 높이 설정 */
    object-fit: cover; /* 이미지 비율 유지하면서 크기 조정 */
    border-radius: 12px 12px 0 0;
}

        .help-content {
            padding: 20px;
            text-align: center;
        }
        .help-content p {
            font-size: 16px;
            color: #555;
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
			<span class="recipe-title">도움말</span> <span class="fresh-date">
				<span class="Today"></span> <span class="date"></span>
			</span>
		</div>
	</div>

    
    <div class="help-container">
        <div class="row row-cols-1 row-cols-md-3 g-4">
            <!-- My 건강 -->
            <div class="col">
                <div class="card help-card h-100">
                    <img src="/mini/resources/help/Myhealth.png" class="help-img" alt="My 건강">
                    <div class="help-content">
                        <h3 class="help-title">My 건강</h3>
                        <p>혈당 수치를 입력하고 건강 데이터를 기반으로 맞춤형 레시피를 추천받을 수 있어요.</p>
                    </div>
                </div>
            </div>

            <!-- My 레시피 -->
            <div class="col">
                <div class="card help-card h-100">
                    <img src="/mini/resources/help/Myrecipe.png" class="help-img" alt="My 레시피">
                    <div class="help-content">
                        <h3 class="help-title">My 레시피</h3>
                        <p>My 냉장고에 있는 재료, 알레르기 정보, 혈당 수치에 맞는 레시피를 추천받아요.</p>
                    </div>
                </div>
            </div>

            <!-- My 냉장고 -->
            <div class="col">
                <div class="card help-card h-100">
                    <img src="/mini/resources/help/Myfridge.png" class="help-img" alt="My 냉장고">
                    <div class="help-content">
                        <h3 class="help-title">My 냉장고</h3>
                        <p>내가 가진 식재료를 관리하고, 유통기한을 확인할 수 있어요.</p>
                        
                        <!-- Accordion for More Details -->
                        <div class="accordion" id="fridgeAccordion">
                            <div class="accordion-item">
                                <h2 class="accordion-header">
                                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#fridgeDetails">
                                        더 많은 정보 보기
                                    </button>
                                </h2>
                                <div id="fridgeDetails" class="accordion-collapse collapse">
                                    <div class="accordion-body text-start">
                                        <p><strong>유통기한 색상 표시:</strong></p>
                                        <ul>
                                            <li>7일 이하 남은 재료: <span style="color: yellow;">노랑색</span></li>
                                            <li>3일 이하 남은 재료: <span style="color: red;">빨강색</span></li>
                                            <li>유통기한이 지난 재료: <strong>경고 메시지 표시</strong></li>
                                        </ul>
                                        <p><strong>재료 추가 방법:</strong></p>
                                        <ul>
                                            <li><strong>상품 사진 인식</strong></li>
                                            <li><strong>영수증 사진 인식</strong></li>
                                            <li><strong>음성 인식</strong></li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                    </div>
                </div>
            </div>

            <!-- 온라인 쇼핑 -->
            <div class="col">
                <div class="card help-card h-100">
                    <img src="/mini/resources/help/Shopping.png" class="help-img" alt="온라인 쇼핑">
                    <div class="help-content">
                        <h3 class="help-title">온라인 쇼핑</h3>
                        <p>부족한 재료를 확인하고 간편하게 구매할 수 있어요.</p>
                    </div>
                </div>
            </div>

            <!-- My 캘린더 -->
            <div class="col">
                <div class="card help-card h-100">
                    <img src="/mini/resources/help/Calendar.png" class="help-img" alt="My 캘린더">
                    <div class="help-content">
                        <h3 class="help-title">My 캘린더</h3>
                        <p>섭취한 레시피를 기록하고 건강한 식단을 계획할 수 있어요.</p>
                    </div>
                </div>
            </div>

            <!-- 커뮤니티 -->
            <div class="col">
                <div class="card help-card h-100">
                    <img src="/mini/resources/help/Community.png" class="help-img" alt="커뮤니티">
                    <div class="help-content">
                        <h3 class="help-title">커뮤니티</h3>
                        <p>자신만의 레시피를 공유하고 다른 사용자들과 소통할 수 있어요.</p>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
