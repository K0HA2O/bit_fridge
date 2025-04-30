<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>냉장고 로딩</title>
<style>
    body {
        margin: 0;
        padding: 0;
        height: 100vh;
        width: 100vw;
        display: flex;
        justify-content: center;
        align-items: center;
        overflow: hidden;
        background-color: #000;
    }

    /* 비디오 컨테이너 */
    #fridgeContainer {
        position: absolute;
        width: 100vw;
        height: 100vh;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    /* MP4 영상 스타일 */
    #fridgeVideo {
        width: 100vw;
        height: 100vh;
        object-fit: cover;
        position: absolute;
        top: 0;
        left: 0;
    }
</style>
</head>
<body>

    <div id="fridgeContainer">
        <!-- MP4 영상 표시 -->
        <video id="fridgeVideo" autoplay playsinline muted>
            <source src="${pageContext.request.contextPath}/resources/video/fridge.mp4" type="video/mp4">
            브라우저가 MP4를 지원하지 않습니다.
        </video>
    </div>

    <script>
        document.addEventListener("DOMContentLoaded", function() {
            const video = document.getElementById("fridgeVideo");

            // GIF가 실행되었는지 확인 (중복 실행 방지)
            if (!sessionStorage.getItem("videoPlayed")) {
                sessionStorage.setItem("videoPlayed", "true"); // 비디오 실행 기록 저장
            }

            // 비디오가 끝나면 마지막 프레임에서 멈추고 냉장고 페이지로 이동
            video.onended = function() {
                sessionStorage.removeItem("videoPlayed"); // GIF가 다시 실행될 수 있도록 초기화
                window.location.href = "${pageContext.request.contextPath}/fridge/fridge";
            };
        });
    </script>

</body>
</html>
