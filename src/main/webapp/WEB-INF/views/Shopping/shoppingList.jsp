<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<title>쇼핑 리스트</title>
<link
   href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
   rel="stylesheet">
   <link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/navbar.css">
<style>
/* ✅ 전체 페이지 배경 */
body {
   background-image: url('resources/img/shopping.avif'); /* 배경 이미지 */
   background-size: cover;
   background-position: center;
   background-attachment: fixed;
   background-repeat: no-repeat;
   background-color: rgba(255, 255, 255, 0.5); /* 배경 투명도 조정 */
	background-blend-mode: overlay; /* 배경 이미지와 색상 혼합 */
}

.container {
   background-color: rgba(255, 255, 255, 0.8); /* 반투명 배경 */
   padding: 20px;
   border-radius: 10px;
   box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);
   margin-top: 40px;
}

.container {
   margin-top: 40px;
   display: flex;
   justify-content: space-between;
   gap: 30px;
}

/* ✅ 쇼핑 리스트 (왼쪽) */
.shopping-list {
   flex: 3; /* 리스트가 더 넓게 차지하도록 설정 */
}

.table-container {
   height: 400px; /* 🔥 높이를 고정 */
   overflow-y: auto; /* 🔥 스크롤 가능하도록 설정 */
   border: 1px solid #ddd;
   border-radius: 8px;
   display: flex;
   flex-direction: column;
}

.table {
   width: 100%;
   border-collapse: collapse;
}

thead {
   background-color: #23983e;
   color: white;
   position: sticky;
   top: 0;
}

th, td {
   text-align: center;
   padding: 12px;
   border: 1px solid #ddd;
}

tbody tr:nth-child(even) {
   background-color: #f9f9f9;
}

.btn-action {
   margin: 5px;
   padding: 6px 12px;
   border-radius: 5px;
   border: none;
   cursor: pointer;
   font-size: 14px;
   transition: all 0.3s ease-in-out;
}

/* ✅ 연한 초록 */
.btn-buy {
   background-color: #CDEECF;
   color: #155724;
}

.btn-buy:hover {
   background-color: #B6E6B6;
}

/* ✅ 연한 빨강 */
.btn-delete {
   background-color: #FCE8E8;
   color: #721C24;
}

.btn-delete:hover {
   background-color: #FADADA;
}

/* ✅ 연한 파랑 */
.btn-coupang {
   background-color: #DCECFB;
   color: #154360;
}

.btn-coupang:hover {
   background-color: #C5E1F7;
}

/* ✅ 쇼핑 리스트 추가 폼 (오른쪽) */
.shopping-form {
   flex: 1;
   background-color: #f8f9fa;
   padding: 20px;
   border-radius: 10px;
   box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);
   height: fit-content;
   width: 350px;
   margin-top: 40px; /* ✅ 기존 125px → 40px로 조정 */
   align-self: flex-start; /* 위로 정렬 */
}

/* 버튼을 가로 정렬 */
.button-group {
   display: flex;
   justify-content: center; /* 가운데 정렬 */
   align-items: center; /* 수직 정렬 */
   gap: 5px; /* 버튼 간격 */
}

/* 버튼 크기 조정 */
.button-group button {
   width: 60px; /* 버튼 크기 동일하게 */
   height: 35px;
   font-size: 14px;
   text-align: center;
   padding: 6px 0;
}

/* ✅ 연한 초록 추가 버튼 */
.btn-add {
   background-color: #A9E5A3;
   color: #1B5E20;
   border-radius: 5px;
   padding: 10px 15px;
   width: 100%;
   font-size: 16px;
}

.btn-add:hover {
   background-color: #91DA8B;
}

/* ✅ 반응형 설정 */
@media ( max-width : 992px) {
   .container {
      flex-direction: column;
      align-items: center;
   }
   .shopping-list {
      width: 100%;
   }
   .shopping-form {
      width: 100%; /* 🔥 모바일에서는 가득 차게 */
      margin-top: 20px;
   }
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
   <div class="container">

      <!-- ✅ 쇼핑 리스트 (왼쪽) -->
      <div class="shopping-list">
         <h1 class="text-center mb-4">🛒 쇼핑 리스트</h1>

         <!-- ✅ 성공 및 오류 메시지 -->
         <c:if test="${not empty success}">
            <p class="text-success text-center">${success}</p>
         </c:if>
         <c:if test="${not empty error}">
            <p class="text-danger text-center">${error}</p>
         </c:if>

         <div class="table-container">
            <!-- 🔥 스크롤 적용 -->
            <table class="table">
               <thead>
                  <tr>
                     <th>번호</th>
                     <th>재료 이름</th>
                     <th>수량</th>
                     <th>단위</th>
                     <th>상태</th>
                     <th>바로가기</th>
                     <th>작업</th>
                  </tr>
               </thead>
               <tbody>
                  <c:forEach var="item" items="${shoppingList}">
                     <tr>
                        <td>${item.listId}</td>
                        <td>${item.ingredientName}</td>
                        <td>${item.quantity}</td>
                        <td>${item.unit}</td>
                        <td><c:choose>
                              <c:when test="${item.status == 'Pending'}">
                      대기 중
                    </c:when>
                              <c:when test="${item.status == 'Purchased'}">
                      ✅ 구매 완료
                    </c:when>
                              <c:otherwise>
                      ${item.status}
                    </c:otherwise>
                           </c:choose></td>
                        <td><a
                           href="https://www.coupang.com/np/search?component=&q=${item.ingredientName}"
                           target="_blank" class="btn btn-action btn-coupang"> Coupang
                        </a></td>
                        <td>
                           <div class="button-group">
                              <!-- 구매 버튼 -->
                              <form action="shopping" method="post">
                                 <input type="hidden" name="action" value="purchase">
                                 <input type="hidden" name="listId" value="${item.listId}">
                                 <button type="submit" class="btn-action btn-buy">구매</button>
                              </form>

                              <!-- 삭제 버튼 -->
                              <form action="shopping" method="post">
                                 <input type="hidden" name="action" value="delete"> <input
                                    type="hidden" name="listId" value="${item.listId}">
                                 <button type="submit" class="btn-action btn-delete">삭제</button>
                              </form>
                           </div>
                        </td>

                     </tr>
                  </c:forEach>
               </tbody>
            </table>
         </div>
      </div>

      <!-- ✅ 쇼핑 리스트 추가 폼 (오른쪽) -->
      <div class="shopping-form">
         <h2 class="text-center">📝 쇼핑 리스트 추가</h2>
         <form action="shopping" method="post">
            <input type="hidden" name="action" value="add">

            <div class="mb-3">
               <label for="ingredientName" class="form-label">재료 이름:</label> <input
                  type="text" class="form-control" name="ingredientName"
                  id="ingredientName" required>
            </div>

            <div class="mb-3">
               <label for="quantity" class="form-label">수량:</label> <input
                  type="number" class="form-control" name="quantity" id="quantity"
                  step="0.1" required>
            </div>

            <div class="mb-3">
               <label for="unit" class="form-label">단위:</label> <input type="text"
                  class="form-control" name="unit" id="unit" value="개">
            </div>

            <button type="submit" class="btn btn-add">추가</button>
         </form>
      </div>
   </div>
</body>
</html>
