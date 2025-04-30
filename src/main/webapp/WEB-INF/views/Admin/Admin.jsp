<%@ page language="java" contentType="text/html; charset=utf-8"
   pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>관리자 페이지</title>
<link
   href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
   rel="stylesheet"
   integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
   crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
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

html, body {
   height: 100%;
   margin: 0;
   display: flex;
   flex-direction: column;
}

h2 {
	font-weight: bold;
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

.main-container {
   flex: 1;
   display: flex;
   flex-direction: column;
}

/* 그래프와 게시글을 좌우 배치 */
.content-wrapper {
   display: flex;
   justify-content: space-between; /* 왼쪽(그래프)과 오른쪽(게시글) 정렬 */
   align-items: flex-start;
   padding: 20px;
   width: 100%;
}

.container {
   flex: 1; /* 컨텐츠가 화면을 꽉 채우도록 설정 */
   min-height: 100vh;
   display: flex;
   flex-direction: column;
   justify-content: space-between;
}

/* 그래프 & 게시글 크기 조정 */
.chart-container {
   width: 50%;
   padding: 20px;
}

.table-container {
   width: 50%;
   padding: 20px;
}

/* 게시글 목록 스크롤 */
.scrollable-table {
   max-height: 400px;
   overflow-y: auto;
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

.content {
   padding: 20px;
   max-width: 800px;
   margin: auto;
}

.section {
   margin-bottom: 20px;
   padding: 15px;
   background-color: #fff;
   border-radius: 10px;
   box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
   display: flex;
   align-items: center;
   justify-content: space-between;
}

.section a {
   text-decoration: none;
   color: #007bff;
   font-size: 14px;
}

.navbar-brand {
   color: #23983e;
   font-weight: bold;
}

.nav-link {
   color: #000000;
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

footer {
   background-color: #f8f9fa;
   padding: 10px 0; /* 내부 패딩 줄이기 */
   text-align: center;
   width: 100%;
   height: auto;
   position: relative;
   bottom: 0;
}

/* 푸터 내부 컨텐츠를 한 줄로 정렬 */
.footer-content {
   display: flex;
   flex-direction: column;
   align-items: center;
   justify-content: center;
   gap: 5px; /* 요소 간격 줄이기 */
   font-size: 14px; /* 텍스트 크기 줄이기 */
}

/* 이용 약관, 개인정보 보호정책 스타일 조정 */
.footer-links {
   display: flex;
   gap: 10px; /* 링크 간격 조정 */
   justify-content: center;
   list-style: none;
   padding: 0;
   margin: 5px 0; /* 위아래 간격 줄이기 */
}

/* 소셜 미디어 링크 정렬 */
.social-media {
   display: flex;
   gap: 10px; /* 아이콘 간격 조정 */
   justify-content: center;
   margin: 5px 0;
}
.recipe-button {
	border: 1px solid #23983e;
	border-radius: 20px;
	padding: 5px 15px;
	background-color: transparent;
	color: #23983e;
	transition: background-color 0.3s;
	margin-right: 40px;
}

.recipe-button:hover {
	background-color: #28a745;
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

   <div class="recipe-header m-2 p-3">
		<div>

		</div>
	</div>



   <main>
      <div class="main-container">
         <div class="content-wrapper">
            <!-- 그래프 섹션 -->
            <div class="chart-container">
               <h2>월별 커뮤니티 이용자 수</h2>
               <form action="postsAndStats" method="get" class="d-flex align-items-center gap-2">
                  <select class="form-control" style="width: 60px;" id="yearSelect" name="year">
                     <option value="2025">2025</option>
                     <option value="2024">2024</option>
                  </select> <select id="monthSelect" name="month" class="form-control" style="width: 60px;">
                     <option value="1">1월</option>
                     <option value="2">2월</option>
                     <option value="3">3월</option>
                     <option value="4">4월</option>
                     <option value="5">5월</option>
                     <option value="6">6월</option>
                     <option value="7">7월</option>
                     <option value="8">8월</option>
                     <option value="9">9월</option>
                     <option value="10">10월</option>
                     <option value="11">11월</option>
                     <option value="12">12월</option>
                  </select>
                  <button type="submit" id="searchButton" class="recipe-button">검색</button>
               </form>
               <canvas id="userStatsChart"></canvas>
            </div>

            <!-- 게시글 섹션 -->
            <div class="table-container">
               <h2>게시글</h2>
               <div class="scrollable-table">
                  <table class="table">
                     <thead>
                        <tr>
                           <th>글 ID</th>
                           <th>제목</th>
                           <th>내용</th>
                           <th>작성자</th>
                           <th>작성일</th>
                        </tr>
                     </thead>
                     <tbody>
                        <c:forEach var="post" items="${posts}">
                           <tr>
                              <td>${post.id}</td>
                              <td>${post.title}</td>
                              <td>${post.content}</td>
                              <td>${post.author}</td>
                              <td>${post.created_at}</td>
                           </tr>
                        </c:forEach>
                     </tbody>
                  </table>
               </div>
            </div>
         </div>
      </div>
   </main>



   <footer>
      <div class="footer-content">
         <p>&copy; 2025 레시피 관리 사이트. All rights reserved.</p>
         <ul class="footer-links">
            <li><a href="#">이용 약관</a></li>
            <li><a href="#">개인정보 보호정책</a></li>
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

   <script type="text/javascript">
    var statsJsonString = '<c:out value="${statsJson}" escapeXml="false"/>';
    if (!statsJsonString || statsJsonString.trim() === "" || statsJsonString === "null") {
        statsJsonString = "[]";
    }

    var statsData = [];
    try {
        statsData = JSON.parse(statsJsonString);
        if (Object.prototype.toString.call(statsData) !== '[object Array]') {
            statsData = [];
        }
    } catch (e) {
        statsData = [];
    }

    function updateChart(data) {
        if (window.myChart) {
            window.myChart.destroy();
        }
        
        var ctx = document.getElementById('userStatsChart').getContext('2d');

        window.myChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: data.map(d => "Week " + d.week),
                datasets: [{
                    label: '게시글 수',
                    data: data.map(d => d.post_count),
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1
                }]
            },
            options: { responsive: true }
        });
    }

    document.addEventListener("DOMContentLoaded", function () {
        updateChart(statsData);
    });
</script>

</body>

</html>	