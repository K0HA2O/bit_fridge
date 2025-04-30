<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>아이디 찾기 결과</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
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
}
a{
text-decoration: none;
}
</style>
</head>
<body>
  <div class="container d-flex justify-content-center align-items-center min-vh-100">
    <div class="card shadow-sm rounded-3" style="width: 400px;">
      <div class="card-body">
        <h5 class="card-title text-center mb-4">아이디 찾기 결과</h5>

        <!-- 아이디 찾기 결과 -->
        <div class="text-center mb-4">
          <p><strong>아이디: </strong>${username}</p> <!-- 이 부분은 실제 아이디로 동적으로 채워져야 합니다. -->
        </div>

        <!-- 로그인 페이지로 돌아가기 버튼 -->
        <div class="text-center" class="gap-3">
          <a href="./" class="btn btn-secondary">로그인</a>
          
        </div>
      </div>
    </div>
  </div>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>