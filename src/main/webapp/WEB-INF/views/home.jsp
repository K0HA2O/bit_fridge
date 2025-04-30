<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>로그인</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
<style>
body {
	margin: 0;
	padding: 0;
	font-family: Arial, sans-serif;
	background-size: cover;
	height: 100vh;
	display: flex;
	justify-content: center;
	align-items: center;
	background-image: url('./resources/img/image0.png');
	background-size: contain;
	background-repeat: no-repeat;
	/* 이미지 반복 방지 */
	background-position: center;
	/* 이미지를 중앙에 위치 */
	background-color: rgba(0, 0, 0, 0.0); /* 배경 투명도 조정 */
	background-blend-mode: overlay; /* 배경 이미지와 색상 혼합 */
}

#loginContainer {
	text-align: center;
	color: #ffffff;
}

#loginContainer h1 {
	font-size: 2.5rem;
	margin-bottom: 20px;
}

#loginButton, #signupButton {
	background-color: rgba(255, 255, 255, 0.8);
	color: #333;
	border: none;
	padding: 10px 20px;
	font-size: 1.2rem;
	border-radius: 5px;
	cursor: pointer;
	transition: transform 0.2s ease, box-shadow 0.2s ease;
	box-shadow: 0 4px 6px rgba(0, 0, 0, 0.2);
}

#loginButton:hover {
	transform: scale(1.05);
	box-shadow: 0 6px 10px rgba(0, 0, 0, 0.3);
}

#loginForm, #signupForm, #findIdForm, #findPwForm {
	display: none;
	margin-top: 20px;
	background-color: rgba(255, 255, 255, 0.332);
	/* 배경색 설정 및 투명도 조정 */
	border-radius: 10px;
	/* 둥근 모서리 */
	width: 350px;
	color: #030303;
}

input[type="text"], input[type="password"], input[type="email"] {
	width: 80%;
	padding: 5px;
	margin: 5px;
	border: 1px solid #ccc;
	border-radius: 4px;
}

button {
	background-color: rgba(255, 255, 255, 0.8);
	color: #333;
	border: none;
	padding: 10px 20px;
	margin-top: 10px;
	border-radius: 6px;
	cursor: pointer;
	transition: background-color 0.3s;
	border-radius: 5px;
	cursor: pointer;
	transition: transform 0.2s ease, box-shadow 0.2s ease;
	box-shadow: 0 4px 6px rgba(0, 0, 0, 0.2);
	margin-right: 10px;
}

/* 아이디 찾기, 비밀번호 찾기 hover */
button:hover {
	transform: scale(1.05);
	box-shadow: 0 6px 10px rgba(0, 0, 0, 0.3);
}

a {
	margin-top: 10px;
	color: #030303;
	text-decoration: none;
}

a:hover {
	border-radius: 3px;
	background-color: rgba(255, 255, 255, 0.8);
	color: #333;
}

h1 {
	margin-top: 80px;
	font-family: "Newsreader-Regular", sans-serif;
}

.listbox {
	width: 200px;
	border: 1px solid #ccc;
	padding: 10px;
	overflow-y: auto;
	max-height: 150px;
	/* 스크롤 가능 */
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
</style>
</head>

<body>
	<div id="loginContainer">
		<h1>My Recipe</h1>
		<button id="loginButton">LOGIN</button>
		<button id="signupButton">Sign Up</button>


		<!-- 로그인 폼 -->
		<div id="loginForm">
			<form action="./login" method="POST">
				<input type="text" id="username" name="username"
					placeholder="아이디를 입력하세요" class="mt-3"> <input
					type="password" id="password" name="password"
					placeholder="비밀번호를 입력하세요"><br> <br>
				<button type="submit" class="btn btn-secondary mb-4">Login</button>
				<br>

				<c:if test="${not empty error}">
					<p style="color: red;">${error}</p>
				</c:if>

				<!-- <button type="submit" formaction="./register_view" formmethod="GET">Sign Up</button> -->
				<div class="d-flex justify-content-center mt-2">
					<a href="javascript:void(0);" id="findIdButton" class="me-3">아이디
						찾기</a> <a href="javascript:void(0);" id="findPwButton">비밀번호 찾기</a>
				</div>
			</form>
		</div>

		<!-- 회원 가입 폼 -->
		<div id="signupForm" class="overflow-auto"
			style="max-height: 400px; text-align: left;">

			<!-- 유효성 검사 에러 메시지 출력 -->
			<c:if test="${not empty errors}">
				<div style="color: red; margin-bottom: 10px;">
					<ul>
						<c:forEach var="entry" items="${errors}">
							<li>${entry.key}=${entry.value}</li>
						</c:forEach>
					</ul>
				</div>
			</c:if>

			<form action="register" method="POST" class="p-3">
				<!-- 아이디 -->
				<div class="mb-3">
					<label for="userId" class="form-label">아이디</label> <input
						type="text" id="userId" name="username" class="form-control"
						value="${username}">
					<div class="error text-danger">${errors.userId}</div>
				</div>

				<!-- 비밀번호 -->
				<div class="mb-3">
					<label for="password" class="form-label">비밀번호</label> <input
						type="password" id="password" name="password" class="form-control">
					<div class="error text-danger">${errors.password}</div>
				</div>

				<!-- 이메일 -->
				<div class="mb-3">
					<label for="email" class="form-label">이메일</label> <input
						type="email" id="email" name="email" class="form-control"
						value="${email}">
					<div class="error text-danger">${errors.email}</div>
				</div>

				<!-- 이름 -->
				<div class="mb-3">
					<label for="name" class="form-label">이름</label> <input type="text"
						id="name" name="name" class="form-control" value="${name}">
					<div class="error text-danger">${errors.username}</div>
				</div>

				<!-- 생년월일 -->
				<div class="mb-3">
					<label for="birthdate" class="form-label">생년월일</label> <input
						type="date" name="birthdate" id="birthdate" class="form-control"
						value="${birthdate}">
				</div>

				<!-- 성별 -->
				<div class="mb-3">
					<label for="gender" class="form-label">성별</label> <select
						id="gender" name="gender" class="form-select">
						<option value="MALE" ${gender=='MALE' ? 'selected' : '' }>남성</option>
						<option value="FEMALE" ${gender=='FEMALE' ? 'selected' : '' }>여성</option>
					</select>
				</div>

				<!-- 건강 상태 -->
				<div class="mb-3">
					<label for="healthinfo" class="form-label">건강 상태</label> <input
						type="text" id="healthinfo" name="healthinfo" class="form-control">
					<small class="text-muted">건강 상태를 입력하세요. 예: 당뇨</small>
				</div>

				<!-- 알레르기 -->
				<div class="mb-3">
					<label class="form-label">알레르기</label>
					<div class="row">
						<div class="col-md-4">
							<div class="form-check">
								<input type="checkbox" id="dairy" name="allergies" value="dairy"
									class="form-check-input"> <label for="dairy"
									class="form-check-label">유제품</label>
							</div>
							<div class="form-check">
								<input type="checkbox" id="egg" name="allergies" value="egg"
									class="form-check-input"> <label for="egg"
									class="form-check-label">달걀</label>
							</div>
							<div class="form-check">
								<input type="checkbox" id="gluten" name="allergies"
									value="gluten" class="form-check-input"> <label
									for="gluten" class="form-check-label">글루텐</label>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-check">
								<input type="checkbox" id="Grain" name="allergies" value="Grain"
									class="form-check-input"> <label for="Grain"
									class="form-check-label">곡물</label>
							</div>
							<div class="form-check">
								<input type="checkbox" id="Peanut" name="allergies"
									value="Peanut" class="form-check-input"> <label
									for="Peanut" class="form-check-label">땅콩</label>
							</div>
							<div class="form-check">
								<input type="checkbox" id="Seafood" name="allergies"
									value="Seafood" class="form-check-input"> <label
									for="Seafood" class="form-check-label">해산물</label>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-check">
								<input type="checkbox" id="Sesame" name="allergies"
									value="Sesame" class="form-check-input"> <label
									for="Sesame" class="form-check-label">참깨</label>
							</div>
							<div class="form-check">
								<input type="checkbox" id="Shellfish" name="allergies"
									value="Shellfish" class="form-check-input"> <label
									for="Shellfish" class="form-check-label">갑각류</label>
							</div>
							<div class="form-check">
								<input type="checkbox" id="Soy" name="allergies" value="Soy"
									class="form-check-input"> <label for="Soy"
									class="form-check-label">콩</label>
							</div>
						</div>
					</div>
				</div>

				<!-- 버튼 -->
				<div class="d-flex justify-content-between">
					<button type="submit" class="btn btn-secondary">회원가입</button>
					<button type="reset" class="btn btn-secondary">다시 입력</button>
				</div>
			</form>
		</div>

		<!-- 아이디 찾기 폼 -->
		<div id="findIdForm" style="text-align: left;">
			<form action="findId" method="POST">
				<div class="p-3">
					<label for="email" class="form-label">이메일 주소</label> <input
						type="email" id="email" name="email" class="form-control"
						placeholder="이메일을 입력하세요" required>
				</div>
				<div class="p-3">
					<label for="birthdate" class="form-label">생년월일</label> <input
						type="text" id="birthdate" name="birthdate" class="form-control"
						placeholder="예: 1999-08-17" required>
				</div>
				<div class="d-flex justify-content-center">
					<button type="submit" class="btn btn-secondary">아이디 찾기</button>
				</div>
			</form>
		</div>

		<!-- 비밀번호 찾기 폼 -->
		<div id="findPwForm">
			<form action="passwordReset" method="post">
				<input type="hidden" name="action" value="send" /> <label>이메일:</label>
				<input type="email" name="email" required />
				<button type="submit" class="btn btn-secondary">인증코드 발송</button>
			</form>
			<c:if test="${not empty message}">
				<p style="color: green;">${message}</p>
				<script>
					// 인증 코드 발송 성공 시 newPasswordView.jsp로 이동
					window.location.href = "newPasswordView.jsp?email=${email}";
				</script>
			</c:if>
			<c:if test="${not empty error}">
				<p style="color: red;">${error}</p>
			</c:if>
		</div>
	</div>



	<script>
		// 초기 폼 상태 설정
		document.getElementById("loginForm").style.display = "none";
		document.getElementById("signupForm").style.display = "none";
		document.getElementById("findIdForm").style.display = "none";
		document.getElementById("findPwForm").style.display = "none";

		// 로그인 버튼 이벤트 처리
		document.getElementById("loginButton").addEventListener(
				"click",
				function() {
					const loginForm = document.getElementById("loginForm");
					const signupForm = document.getElementById("signupForm"); // 회원가입 폼 가져오기
					const findIdForm = document.getElementById("findIdForm"); // 아이디 찾기 폼 가져오기
					const findPwForm = document.getElementById("findPwForm"); // 아이디 찾기 폼 가져오기
					if (loginForm.style.display === "none"
							|| loginForm.style.display === "") {
						loginForm.style.display = "block";
						signupForm.style.display = "none"; // 다른 폼 숨기기
						findIdForm.style.display = "none"; // 아이디 찾기 폼 숨기기
						findPwForm.style.display = "none"; // 비밀번호 찾기 폼 숨기기
					} else {
						loginForm.style.display = "none";
					}
				});

		// 회원가입 버튼 이벤트 처리
		document.getElementById("signupButton").addEventListener(
				"click",
				function() {
					const loginForm = document.getElementById("loginForm");
					const signupForm = document.getElementById("signupForm");
					const findIdForm = document.getElementById("findIdForm"); // 아이디 찾기 폼 가져오기
					const findPwForm = document.getElementById("findPwForm"); // 비밀번호 찾기 폼 가져오기
					if (signupForm.style.display === "none"
							|| signupForm.style.display === "") {
						signupForm.style.display = "block";
						loginForm.style.display = "none"; // 다른 폼 숨기기
						findIdForm.style.display = "none"; // 아이디 찾기 폼 숨기기
						findPwForm.style.display = "none"; // 비밀번호 찾기 폼 숨기기
					} else {
						signupForm.style.display = "none";
					}
				});

		document
				.getElementById('findIdButton')
				.addEventListener(
						'click',
						function() {
							document.getElementById('findIdForm').style.display = 'block';
							document.getElementById('loginForm').style.display = 'none';
							document.getElementById('findPwForm').style.display = 'none';
						});

		document
				.getElementById('findPwButton')
				.addEventListener(
						'click',
						function() {
							document.getElementById('findPwForm').style.display = 'block';
							document.getElementById('loginForm').style.display = 'none';
							document.getElementById('findIdForm').style.display = 'none';
						});
	</script>

</body>

</html>