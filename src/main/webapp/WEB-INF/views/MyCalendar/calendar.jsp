<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My 캘린더</title>
<script
	src='https://cdn.jsdelivr.net/npm/@fullcalendar/core@6.1.15/index.global.min.js'></script>
<script
	src='https://cdn.jsdelivr.net/npm/@fullcalendar/daygrid@6.1.15/index.global.min.js'></script>
<script
	src='https://cdn.jsdelivr.net/npm/@fullcalendar/interaction@6.1.15/index.global.min.js'></script>
<script>
          document.addEventListener('DOMContentLoaded', function() {
            var calendarEl = document.getElementById('calendar');
            var events = JSON.parse('${events}');
                        
            var formattedEvents = events.map(event => ({
            	id: event.id,
            	title: event.title,
            	start: event.date,
            	url: './recipe/recipeInfo?id=' + event.recipe_id
            }));
            
            var calendar = new FullCalendar.Calendar(calendarEl, {
              initialView: 'dayGridMonth',
              editable: true,
              locale: 'ko',
              headerToolbar: {
            	  left: 'customTitle',
            	  center: 'title'
              },
              customButtons: {
            	customTitle: {
            		text: 'My 캘린더'
            	}  
              },
              events: formattedEvents,
              eventClick: function(info) {
            	  info.jsEvent.preventDefault();
            	  
            	  var action = confirm('상세 페이지로 이동하시겠습니까?\n삭제를 원하시면 "취소"를 클릭해주세요.');
            	  
            	  if (action) {
            		  window.location.href = info.event.url;
            	  } else {
            		  if (confirm('정말 삭제하시겠습니까?')) {
            			  window.location.href = './calendar/delete?id=' + info.event.id;
            		  }
            	  }
              },
              eventDrop: function(info) {
            	  var eventId = info.event.id;
            	  var newDate = info.event.start;
            	  
            	  var localDate = newDate.toLocaleDateString("en-CA")
            	  
            	  console.log("이벤트 이동됨: ID =", eventId, "새로운 날짜 = ", localDate);
            	  
            	  var xhr = new XMLHttpRequest();
            	  xhr.open("POST", "./calendar/update", true);
            	  xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            	  xhr.send("id=" + eventId + "&event_date=" + localDate);
            	  
            	  xhr.onload = function() {
            		  if (xhr.status === 200) {
            			  alert("날짜가 변경되었습니다.");
            		  } else {
            			  alert("날짜 변경에 실패했습니다.");
            			  info.revert(); // 변경 취소
            		  }
            	  };
              }
            });

            calendar.render();
          });
        </script>
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
#calendar {
    width: 100%;
    height: 750px; /* 기존보다 크기 축소 */
  }
.fc-event {
	background-color: rgba(80, 150, 62, 0.3) !important;
	border: none !important;
}
.fc-daygrid-day-number {
	color: black !important;
	text-decoration: none;
}
.fc-button.fc-today-button {
	background-color:#6c757d !important;
	color: white !important;
	border-radius: 5px;
	padding: 5px 15px;
	border: none !important;
}
.fc-button.fc-prev-button,
.fc-button.fc-next-button {
	background-color:#6c757d !important;
	color: white !important;
	border-radius: 5px;
	padding: 5px 15px;
	border: none !important;
}
.fc-button.fc-prev-button:hover, 
.fc-button.fc-next-button:hover, 
.fc-button.fc-today-button:hover {
  background-color: #495057 !important; 
}
.fc-customTitle-button {
	background-color: white !important;
	color: black !important;
	font-weight: bold !important;
	font-size: 2em !important;
	border : none !important;
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

	<div class="recipe-header m-2 p-3">
		<div>

		</div>
	</div>

	<!-- 무조건 <main> 태그 안에 자신이 구현할 것 넣으면 됩니다!!!! -->
	<main>
		<div class="container text-center mt-4">
			<div>
				<div class="calendar" id='calendar'></div>
				<br>
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