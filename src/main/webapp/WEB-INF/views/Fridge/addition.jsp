<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/navbar.css">
<meta charset="UTF-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/navbar.css">
	<!-- 시간 js -->
<script
	src="${pageContext.request.contextPath}/resources/js/dateHandler.js"></script>
<title>파일 업로드</title>
<style>
.container {
	margin-top: 20px;
}

.card {
	border-radius: 10px;
	box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);
	padding: 20px;
	text-align: center;
}

.form-group {
	margin-bottom: 15px;
}

.upload-btn {
	background-color: #23983e;
	color: white;
	border-radius: 5px;
	padding: 10px 15px;
	text-decoration: none;
	border: none;
	transition: background-color 0.3s ease;
}

.upload-btn:hover {
	background-color: #1e7e34;
}

.error {
	color: red;
	margin-top: 10px;
}

#audioPlayback {
	display: none;
	margin-top: 10px;
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
			<span class="recipe-title">파일업로드</span> <span class="fresh-date">
				<span class="Today"></span> <span class="date"></span>
			</span> </span>
		</div>
	</div>
	<main>
		<div class="container">
			<div class="row justify-content-center">
				<div class="col-md-6" style="width: 30%">

					<h3 class="text-left mb-4">파일 업로드</h3>
					<div class="card">
						<form action="${pageContext.request.contextPath}/fridge/addition"
							method="post" enctype="multipart/form-data">
							<div class="form-group">
								<input type="file" id="file" name="file"
									accept=".jpg,.png,.pdf,.mp3,.wav,.ogg" required
									class="form-control"> <select id="type" name="type"
									required class="form-control mt-2">
									<option value="object">상품사진으로 등록하기</option>
									<option value="receipt">영수증으로 등록하기</option>
									<option value="voice">음성파일로 등록하기</option>
								</select>
							</div>

							<button type="submit" class="upload-btn w-100 mt-3">업로드</button>
						</form>

						<!-- 에러 메시지 표시 -->
						<c:if test="${not empty error}">
							<p class="error">${error}</p>
						</c:if>

						<hr>

						<!-- 실시간 음성 입력 기능 -->
						<h5>음성 입력</h5>


						<!-- 음성 업로드 폼 -->
						<form id="voiceForm"
							action="${pageContext.request.contextPath}/fridge/uploadVoice"
							method="post" enctype="multipart/form-data">
							<button type="button" id="startRecording" class="btn btn-primary">녹음
								시작</button>
							<button type="button" id="stopRecording" class="btn btn-danger"
								disabled>녹음 중지</button>
							<audio id="audioPlayback" controls></audio>
							<input type="hidden" id="voiceData" name="voiceData">
							<button type="submit" class="upload-btn w-100 mt-2"
								id="voiceUploadBtn" disabled>음성 업로드</button>
						</form>

						<c:if test="${not empty recognizedText}">
							<p class="mt-3">
								<strong>인식된 텍스트:</strong> ${recognizedText}
							</p>
						</c:if>

						<c:if test="${not empty error}">
							<p class="error">${error}</p>
						</c:if>
					</div>

				</div>
			</div>
		</div>
	</main>
	<script>
    let mediaRecorder;
    let audioChunks = [];

    document.getElementById("startRecording").addEventListener("click", async () => {
      const stream = await navigator.mediaDevices.getUserMedia({ audio: true });
      mediaRecorder = new MediaRecorder(stream);
      audioChunks = [];

      mediaRecorder.ondataavailable = (event) => {
        audioChunks.push(event.data);
      };

      mediaRecorder.onstop = async () => {
        const audioBlob = new Blob(audioChunks, { type: "audio/wav" });
        const audioUrl = URL.createObjectURL(audioBlob);
        document.getElementById("audioPlayback").src = audioUrl;
        document.getElementById("audioPlayback").style.display = "block";

        // 음성 데이터를 Blob URL로 저장
        const reader = new FileReader();
        reader.readAsDataURL(audioBlob);
        reader.onloadend = () => {
          document.getElementById("voiceData").value = reader.result;
          document.getElementById("voiceUploadBtn").disabled = false;
        };
      };

      mediaRecorder.start();
      document.getElementById("startRecording").disabled = true;
      document.getElementById("stopRecording").disabled = false;
    });

    document.getElementById("stopRecording").addEventListener("click", () => {
      mediaRecorder.stop();
      document.getElementById("startRecording").disabled = false;
      document.getElementById("stopRecording").disabled = true;
    });
  </script>

</body>
</html>
