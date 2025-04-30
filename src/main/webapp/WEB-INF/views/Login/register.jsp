<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<style>
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
</style>
</head>
<body>
	<h1>회원가입</h1>

	<!-- 유효성 검사 에러 메시지 출력 -->
	<c:if test="${not empty errors}">
		<div style="color: red; margin-bottom: 10px;">
			<ul>
				<c:forEach var="entry" items="${errors}">
					<li>${entry.key}= ${entry.value}</li>
				</c:forEach>
			</ul>
		</div>
	</c:if>

	<form action="register" method="POST">

		<label for="username">아이디:</label> <input type="text" id="userId"
			name="username" value="${username}">
		<div class="error">${errors.userId}</div>
		<br>
		
		<br> <label for="password">비밀번호:</label> <input type="password"
			id="password" name="password">
		<div class="error">${errors.password}</div>
		<br>
		
		<br> <label for="email">이메일:</label> <input type="email"
			id="email" name="email" value="${email}">
		<div class="error">${errors.email}</div>
		<br>
		
		<br> <label for="name">이름:</label> <input type="text" id="name"
			name="name" value="${name}">
		<div class="error">${errors.username}</div>
		<br>
		
		<br> <label for="birthdate">생년월일:</label> <input type="date"
			name="birthdate" id="birthdate" value="${birthdate}"> <br>
			
		<br> <label for="gender">성별:</label> <select id="gender"
			name="gender">
			<option value="MALE" ${gender == 'MALE' ? 'selected' : ''}>남성</option>
			<option value="FEMALE" ${gender == 'FEMALE' ? 'selected' : ''}>여성</option>
		</select> <br> <br> 
		
		<label for="healthinfo">건강 상태:</label> <input
			type="text" id="healthinfo" name="healthinfo">
		<p class="hint">건강 상태를 입력하세요. 예: 당뇨</p>
		<br>
		<br> 
		
		<label for="allergies">알레르기:</label>
		<div class="listbox">
			<div class="listbox-item">
				<input type="checkbox" id="dairy" name="allergies" value="dairy"> <label for="dairy">유제품</label>
			</div>
			<div class="listbox-item">
				<input type="checkbox" id="egg" name="allergies" value="egg"> <label for="egg">달걀</label>
			</div>
			<div class="listbox-item">
				<input type="checkbox" id="gluten" name="allergies" value="gluten"> <label for="gluten">글루텐</label>
			</div>
			<div class="listbox-item">
				<input type="checkbox" id="Grain" name="allergies" value="Grain"> <label for="Grain">곡물</label>
			</div>
			<div class="listbox-item">
				<input type="checkbox" id="Peanut" name="allergies" value="Peanut"> <label for="Peanut">땅콩</label>
			</div>
			<div class="listbox-item">
				<input type="checkbox" id="Seafood" name="allergies" value="Seafood"> <label for="Seafood">해산물</label>
			</div>
			<div class="listbox-item">
				<input type="checkbox" id="Sesame" name="allergies" value="Sesame"> <label for="Sesame">참깨</label>
			</div>
			<div class="listbox-item">
				<input type="checkbox" id="Shellfish" name="allergies" value="Shellfish"> <label
					for="Shellfish">갑각류</label>
			</div>
			<div class="listbox-item">
				<input type="checkbox" id="Soy" name="allergies" value="Soy"> <label for="Soy">콩</label>
			</div>
			<div class="listbox-item">
				<input type="checkbox" id="Sulfite" name="allergies" value="Sulfite"> <label for="Sulfite">황산염</label>
			</div>
			<div class="listbox-item">
				<input type="checkbox" id="TreeNut" name="allergies" value="TreeNut"> <label for="TreeNut">견과류</label>
			</div>
			<div class="listbox-item">
				<input type="checkbox" id="Wheat" name="allergies" value="Wheat"> <label for="Wheat">밀</label>
			</div>
		</div>
		<br>
		<br>

		<button type="submit">회원가입</button>
		<button type="reset">다시 입력</button>
	</form>
</body>
</html>