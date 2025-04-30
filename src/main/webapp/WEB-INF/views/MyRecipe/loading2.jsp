<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>레시피 로딩</title>
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
    #recipeContainer {
        position: absolute;
        width: 100vw;
        height: 100vh;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    /* MP4 영상 스타일 */
    #recipeVideo {
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

    <div id="recipeContainer">
        <!-- MP4 영상 표시 -->
        <video id="recipeVideo" autoplay playsinline muted>
            <source src="${pageContext.request.contextPath}/resources/video/recipe.mp4" type="video/mp4">
            브라우저가 MP4를 지원하지 않습니다.
        </video>
    </div>

    <script>
    document.addEventListener("DOMContentLoaded", function() {
        const video = document.getElementById("recipeVideo");

        if (!sessionStorage.getItem("videoPlayed")) {
            sessionStorage.setItem("videoPlayed", "true"); 
        }

        video.onended = function() {
            sessionStorage.removeItem("videoPlayed"); // GIF가 다시 실행될 수 있도록 초기화
            window.location.href = "${pageContext.request.contextPath}/recipe";
        };
    });

    </script>

</body>
</html>
