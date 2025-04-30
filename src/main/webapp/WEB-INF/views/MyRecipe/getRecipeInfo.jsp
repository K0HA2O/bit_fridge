<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Recipe Details</title>
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
</style>
</head>
<body class="p-4">

   <header>
      <nav class="navbar navbar-expand-lg m-2 p-2">
         <div class="container-fluid mx-5">
            <a class="navbar-brand" href="#">냉장고를 부탁해</a>
            <button class="navbar-toggler" type="button"
               data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown"
               aria-controls="navbarNavDropdown" aria-expanded="false"
               aria-label="Toggle navigation">
               <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse">
               <ul class="navbar-nav ms-auto gap-5" style="font-weight: bold;">
                  <a class="nav-link"
                     href="${pageContext.request.contextPath}/fridge/loading">My
                     냉장고</a>
                  <a class="nav-link"
                     href="${pageContext.request.contextPath}/recipe/loading2">My
                     레시피</a>
                  <li class="nav-item"><a class="nav-link" href="../health">My
                        건강</a></li>
                  <li class="nav-item"><a class="nav-link" href="../calendar">My
                        캘린더</a></li>
                  <li class="nav-item"><a class="nav-link" href="#">온라인 쇼핑</a></li>
                  <li class="nav-item"><a class="nav-link" href="../community">커뮤니티</a></li>
               </ul>
               <ul class="navbar-nav ms-auto">
                  <li class="nav-item"><a class="nav-link custom-button"
                     href="#">MyPage</a></li>
               </ul>
            </div>
         </div>
      </nav>
   </header>

      <div class="recipe-header m-2 p-5">
         <div>
            <span class="recipe-title">레시피 정보</span> <span class="fresh-date">
               <span class="fresh-august-21-2025-span">Fresh</span> <span
               class="fresh-august-21-2025-span2">— August 21, 2025</span>
            </span>
         </div>
         <div class="recipe-buttons">
            <form action="../calendar/add" method="post" style="display: inline;">
               <input type="hidden" name="id" value="${id}"> <input
                  type="hidden" name="title" value="${title}"> <input
                  type="date" name="date" required>
               <button type="submit" class="recipe-button btn btn-outline-success">캘린더에
                  추가</button>
            </form>
         </div>
      </div>
   
   <main>
    <div class="container text-center mt-4">
        <div>
            <c:choose>
                <c:when test="${not empty title}">
                    <h1>${title}</h1>

                    <!-- 사진, 재료, 부족한 재료를 가로로 정렬하는 컨테이너 -->
                    <div style="display: flex; align-items: flex-start; justify-content: center; gap: 30px;">
                        <!-- 왼쪽: 음식 이미지 -->
                        <div>
                            <img src="${image}" alt="${title}"
                                style="max-width: 100%; height: auto; border-radius: 10px; box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);" />
                            <h2 style="text-align: center;">인분 : ${servings}</h2>
                        </div>

                        <!-- 가운데: 재료 목록 -->
                        <div class="recipe-container" style="width: 300px; max-height: 400px; overflow-y: auto; padding: 10px; border: 1px solid #ddd; border-radius: 10px; background-color: #f9f9f9;">
                            <h3 style="text-align: center;">재료</h3>
                            <ul style="list-style: none; padding: 0;">
                                <c:forEach var="ingredient" items="${ingredients}">
                                    <li style="padding: 5px 0; font-size: 16px; border-bottom: 1px solid #ddd;">
                                        ${ingredient.translatedOriginal} (${ingredient.amount} ${ingredient.unit})
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>

                        <!-- 오른쪽: 부족한 재료 목록 -->
                        <div class="missing-container" style="width: 300px; max-height: 400px; overflow-y: auto; padding: 10px; border: 1px solid red; border-radius: 10px; background-color: #fff5f5;">
                            <h3 style="text-align: center; color: red;">부족한 재료</h3>
                            <ul style="list-style: none; padding: 0; color: red;">
                                <c:forEach var="ingredient" items="${missingIngredients}">
                                    <li style="padding: 5px 0; font-size: 16px; border-bottom: 1px solid #ddd;">
                                        ${ingredient.translatedOriginal} (${ingredient.amount} ${ingredient.unit})
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>

                    <!-- 조리 방법 -->
<h3 class="text-center mt-4">조리 방법</h3>

<!-- 조리 방법 박스 -->
<div class="instructions-box mx-auto p-4 my-3">
    <p id="instruction-text" class="text-center">첫 번째 단계를 보려면 버튼을 눌러주세요.</p>
</div>

<!-- 이전 / 다음 버튼 -->

<div class="d-flex justify-content-center gap-4 my-3">
    <button id="prev-step-btn" class="nav-btn" disabled>&lt;</button>
    <button id="next-step-btn" class="nav-btn">&gt;</button>
</div>


<style>
/* 조리 방법 박스 스타일 */
.instructions-box {
    max-width: 1100px;
    min-height: 80px; /* 높이를 조정하여 일관된 디자인 유지 */
    background-color: #f8f9fa;
    border: 1px solid #ddd;
    border-radius: 10px;
    box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);
    text-align: center;
    font-size: 1.3em; /* 폰트 크기 증가 */
    line-height: 1.6; /* 줄 간격 증가 */
    padding: 20px; /* 패딩 증가 */
    display: flex;
    align-items: center;
    justify-content: center;
    margin: 0 auto;
}

/* 버튼 스타일 변경 */
.nav-btn {
    width: 60px; /* 버튼 크기 */
    height: 60px;
    border: 2px solid black; /* 검정색 테두리 */
    border-radius: 10px; /* 둥근 모서리 */
    background-color: white; /* 배경색 */
    color: black; /* 텍스트(아이콘) 색상 */
    font-size: 1.5em;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    transition: all 0.3s ease-in-out;
}

/* 버튼 hover 효과 */
.nav-btn:hover {
    background-color: black; /* 배경색 검정 */
    color: white; /* 텍스트(아이콘) 색상 변경 */
}

/* 비활성화 버튼 */
.nav-btn:disabled {
    border-color: #ccc;
    color: #ccc;
    cursor: not-allowed;
    background-color: #f8f9fa;
}

</style>
                </c:when>

                <c:otherwise>
                    <p>No recipe details available.</p>
                </c:otherwise>
            </c:choose>

            <c:if test="${not empty error}">
                <p style="color: red;">Error: ${error}</p>
            </c:if>

            <button id="cookCompleteBtn" type="button" class="btn btn-outline-success" data-recipe-id="${id}">조리 완료</button>
            <button id="buyMissingIngredientsBtn" class="btn btn-warning" data-recipe-id="${id}">부족한 재료 구매</button>

            <br><br>
            <a href="${spoonacularSourceUrl}">Spoonacular에서 더 많은 정보 알아보기</a>
            <br><br>
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


   <script>
   document.addEventListener("DOMContentLoaded", function () {
       console.log("✅ DOMContentLoaded 실행됨");

       // 조리 완료 버튼 기능
       let cookButton = document.getElementById("cookCompleteBtn");
       if (cookButton) {
           cookButton.addEventListener("click", function () {
               let recipeId = this.getAttribute("data-recipe-id");
               let ingredients = [];

               // 재료 목록에서 데이터 추출
               document.querySelectorAll(".recipe-container ul li").forEach(item => {
                   let text = item.innerText.trim();
                   let parts = text.match(/(.+)\s\(([\d.]+)\s(.+)\)/); // 정규식으로 재료명, 수량, 단위 추출

                   if (parts) {
                       let name = parts[1].trim();
                       let quantity = parseFloat(parts[2]);
                       let unit = parts[3].trim();
                       ingredients.push({ name: name, quantity: quantity, unit: unit });
                   }
               });

               console.log("✅ 조리 완료 전송 데이터:", ingredients);

               fetch("http://localhost:8080/mini/fridge/updateIngredients", {
                   method: "POST",
                   headers: { "Content-Type": "application/json", "Accept": "application/json" },
                   body: JSON.stringify({ recipeId: recipeId, ingredients: ingredients })
               })
               .then(response => response.json())
               .then(data => {
                   if (data.success) {
                       alert("✅ 조리가 완료되었습니다! 냉장고 재료가 차감되었습니다.");
                       window.location.href = "../fridge/fridge";
                   } else {
                       alert("❌ 조리 완료 처리 중 오류 발생: " + data.message);
                   }
               })
               .catch(error => {
                   console.error("🚨 요청 오류:", error);
                   alert("❌ 서버 요청 중 문제가 발생했습니다. 다시 시도해주세요.");
               });
           });
       } else {
           console.warn("❌ [ERROR] '조리 완료' 버튼이 존재하지 않습니다.");
       }

       // 부족한 재료 구매 버튼 기능
       document.getElementById("buyMissingIngredientsBtn")?.addEventListener("click", function () {
           console.log("✅ [DEBUG] '부족한 재료 구매' 버튼 클릭됨!");

           let recipeId = this.getAttribute("data-recipe-id");
           let missingIngredients = [];

           // 부족한 재료 목록에서 데이터 추출
           document.querySelectorAll(".missing-container ul li").forEach(item => {
               let text = item.innerText.trim();
               let parts = text.match(/(.+)\s\(([\d.]+)\s(.+)\)/); // 정규식으로 재료명, 수량, 단위 추출

               if (parts) {
                   let name = parts[1].trim();
                   let quantity = parseFloat(parts[2]);
                   let unit = parts[3].trim();
                   missingIngredients.push({ name: name, quantity: quantity, unit: unit });
               }
           });

           console.log("🔍 전송할 부족한 재료 목록:", missingIngredients);

           if (missingIngredients.length === 0) {
               alert("❌ 추가할 부족한 재료가 없습니다!");
               return;
           }

           fetch("http://localhost:8080/mini/shopping?action=addMissingIngredients", {
               method: "POST",
               headers: { "Content-Type": "application/json; charset=UTF-8", "Accept": "application/json" },
               body: JSON.stringify({ recipeId: recipeId, ingredients: missingIngredients })
           })
           .then(response => response.json())
           .then(data => {
               console.log("✅ 서버 응답:", data);
               if (data.success) {
                   alert("✅ 부족한 재료가 쇼핑 리스트에 추가되었습니다!");
                   window.location.href = "../shopping";
               } else {
                   alert("❌ 쇼핑 리스트 추가 중 오류 발생: " + data.message);
               }
           })
           .catch(error => {
               console.error("🚨 요청 오류:", error);
               alert("❌ 서버 요청 중 문제가 발생했습니다. 다시 시도해주세요.");
           });
       });
   });
     document.addEventListener("DOMContentLoaded", function () {
           let instructions = `${instructions}`.split(". ").filter(step => step.trim() !== "");
           let currentStep = -1;
           let instructionText = document.getElementById("instruction-text");
           let nextStepBtn = document.getElementById("next-step-btn");
           let prevStepBtn = document.getElementById("prev-step-btn");

           function updateButtons() {
               prevStepBtn.disabled = currentStep <= 0;
               nextStepBtn.disabled = currentStep >= instructions.length - 1;
           }

           if (nextStepBtn && prevStepBtn) {
               nextStepBtn.addEventListener("click", function () {
                   if (currentStep < instructions.length - 1) {
                       currentStep++;
                       instructionText.innerText = (currentStep + 1) + ". " + instructions[currentStep];
                       updateButtons();
                   }
               });

               prevStepBtn.addEventListener("click", function () {
                   if (currentStep > 0) {
                       currentStep--;
                       instructionText.innerText = (currentStep + 1) + ". " + instructions[currentStep];
                       updateButtons();
                   }
               });

               updateButtons();
           } else {
               console.warn("❌ [ERROR] 조리 방법 버튼이 존재하지 않습니다.");
           }
       })
</script>

</body>
