<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My 건강</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/navbar.css">
<!-- 시간 js -->
<script
	src="${pageContext.request.contextPath}/resources/js/dateHandler.js"></script>
<style>
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

.active-button {
	background-color: #23983e;
	color: white;
}

.btn {
	border-radius: 20px;
	padding: 5px 15px;
	background-color: transparent;
	transition: background-color 0.3s;
	margin-right: 15px;
}
</style>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>

<body class="p-4">

	<header>
		<nav class="navbar navbar-expand-lg mt-2 ms-4 me-4 pt-2 ps-4 pe-4">
			<div class="container-fluid">
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
			<span class="recipe-title">My 건강</span> <span class="fresh-date">
				<span class="Today"></span> <span class="date"></span>
			</span> </span>
		</div>

		<div class="recipe-buttons">
			<input type="date" id="startDate"
				class="form-control w-auto d-inline" onchange="applyDateFilter()">

			<button class="recipe-button" id="morningBtn"
				onclick="showChart('morning')">아침</button>
			<button class="recipe-button" id="lunchBtn"
				onclick="showChart('lunch')">점심</button>
			<button class="recipe-button" id="dinnerBtn"
				onclick="showChart('dinner')">저녁</button>
		</div>
	</div>
	<main>
		<div class="container text-center mt-4">

			<canvas id="bloodSugarChart" width="800" height="310"></canvas>

			<div class="text-center">
				<form action="./health/registerBloodSugar" method="POST"
					class="d-flex align-items-center justify-content-center gap-2"
					onsubmit="return confirmBloodSugarUpdate(this.mealTime.value, this.mealStatus.value)">

					<select name="mealTime" class="form-select w-auto">
						<option value="아침">아침</option>
						<option value="점심">점심</option>
						<option value="저녁">저녁</option>
					</select> <select name="mealStatus" class="form-select w-auto">
						<option value="전">식사전</option>
						<option value="후">식사후</option>
					</select> <input type="number" name="bloodSugarLevel"
						class="form-control w-auto" placeholder="혈당 수치 입력" required>
					<button type="submit" class="recipe-button btn btn-outline-success">등록</button>
					<br> <br>
				</form>
			</div>

			<script>
				let morningBefore = JSON.parse('${morningBefore}');
				let morningAfter = JSON.parse('${morningAfter}');
				let lunchBefore = JSON.parse('${lunchBefore}');
				let lunchAfter = JSON.parse('${lunchAfter}');
				let dinnerBefore = JSON.parse('${dinnerBefore}');
				let dinnerAfter = JSON.parse('${dinnerAfter}');
				
				const originalData = {
					    morningBefore: [...morningBefore],
					    morningAfter: [...morningAfter],
					    lunchBefore: [...lunchBefore],
					    lunchAfter: [...lunchAfter],
					    dinnerBefore: [...dinnerBefore],
					    dinnerAfter: [...dinnerAfter]
					};

				
				let chartInstance = null;
				
				function showChart(meal) {
					let dataBefore, dataAfter;
					currentMeal = meal;
					
					document.querySelectorAll('.recipe-button').forEach(button => {
						button.classList.remove('active-button');
					});
					
					document.querySelector('select[name="mealTime"]').value =
						meal === 'morning' ? '아침' :
						meal === 'lunch' ? '점심' : '저녁';
					
					if (meal === 'morning') {
						document.getElementById('morningBtn').classList.add('active-button');
						dataBefore = morningBefore || [];
						dataAfter = morningAfter || [];
					} else if (meal === 'lunch') {
						document.getElementById('lunchBtn').classList.add('active-button');
						dataBefore = lunchBefore || [];
						dataAfter = lunchAfter || [];
					} else {
						document.getElementById('dinnerBtn').classList.add('active-button');
						dataBefore = dinnerBefore || [];
						dataAfter = dinnerAfter || [];
					}
					
					const labels = [...new Set([...dataBefore, ...dataAfter].map(d => d.date))].sort();
					const recentLabels = labels.slice(-7);
					
					const bloodSugarBefore = labels.map(date => {
						const entry = dataBefore.find(d => d.date === date);
						return entry ? entry.blood_sugar_level : 0;
					});
					
					const bloodSugarAfter = labels.map(date => {
						const entry = dataAfter.find(d => d.date === date);
						return entry ? entry.blood_sugar_level : 0;
					});
					
					const ctx = document.getElementById("bloodSugarChart").getContext("2d");
					
					if (chartInstance) {
						chartInstance.destroy();
					}
					
					chartInstance = new Chart(ctx, {
						type: "bar",
						data: {
							labels: recentLabels,
							datasets: [
								{
									label: "식사전 혈당",
									data: bloodSugarBefore,
									backgroundColor: "rgba(255, 99, 132, 0.5)",
									barThickness: 20
								},
								{
									label: "식사후 혈당",
									data: bloodSugarAfter,
									backgroundColor: "rgba(54, 162, 235, 0.5)",
									barThickness: 20
								}
							]
						},
						options: {
							responsive: true,
							scales: {
			                    x: {
			                        title: {
			                            display: true,
			                            text: "날짜"
			                        },
			                        ticks: {
			                        	maxTicksLimit: 7,
			                        	autoSkip: true
			                        }
			                    },
			                    y: {
			                        title: {
			                            display: true,
			                            text: "혈당 수치"
			                        },
			                        beginAtZero: true
			                    }
			                }
						}
					});
				}
				
				window.onload = function() {
					showChart('morning');
				}
				
				// 이미 입력한 데이터가 있을 때
				function confirmBloodSugarUpdate(mealTime, mealStatus) {
					const currentBloodSugar = getCurrentBloodSugar(mealTime, mealStatus);
					
					if (currentBloodSugar !== null) {
						if (confirm("기존 혈당 수치를 수정하시겠습니까?")) {
							return true;
						} else {
							return false;
						}
					}
					return true; // 기존 데이터가 없으면 그냥 등록
				}
				
				function getCurrentBloodSugar(mealTime, mealStatus) {
					let existingData = null;
					
					switch (mealTime) {
						case '아침' :
							existingData = mealStatus === '전' ? ${morningBefore} : ${morningAfter};
							break;
						case '점심' :
							existingData = mealStatus === '전' ? ${lunchBefore} : ${lunchAfter};
							break;
						case '저녁' :
							existingData = mealStatus === '전' ? ${dinnerBefore} : ${dinnerAfter};
							break;
					}
					
					return existingData.length > 0 ? existingData[existingData.length - 1].blood_sugar_level : null;
				}
				
				function applyDateFilter() {
					const startDate = document.getElementById('startDate').value;
					if (!startDate) {
						alert("날짜를 선택하세요.");
						return;
					}
					
					const start = new Date(startDate);
					const end = new Date(start);
					end.setDate(end.getDate() + 6); // 선택한 날짜부터 7일
					
					function filterData(data) {
						return data.filter(d => {
							const date = new Date(d.date);
							return date >= start && date <= end;
						});
					}
					
					morningBefore = filterData(originalData.morningBefore);
				    morningAfter = filterData(originalData.morningAfter);
				    lunchBefore = filterData(originalData.lunchBefore);
				    lunchAfter = filterData(originalData.lunchAfter);
				    dinnerBefore = filterData(originalData.dinnerBefore);
				    dinnerAfter = filterData(originalData.dinnerAfter);
					
					showChart(currentMeal);
				}
			</script>

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