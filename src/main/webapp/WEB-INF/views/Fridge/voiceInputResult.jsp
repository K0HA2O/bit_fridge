<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>음성 입력 결과</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<style>
  .container { margin-top: 20px; }
  table {
    width: 100%;
    border-collapse: collapse;
  }
  th, td {
    text-align: center;
    padding: 12px;
    border: 1px solid #ddd;
  }
  thead {
    background-color: #23983e;
    color: white;
  }
  tbody tr:nth-child(even) {
    background-color: #f9f9f9;
  }
  .form-control {
    text-align: center;
  }
  .save-btn {
    margin-top: 15px;
    background-color: #28a745;
    color: white;
    border: none;
    padding: 10px 20px;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s;
  }
  .save-btn:hover {
    background-color: #00721b;
  }
  .recognized-text {
    font-weight: bold;
    color: #333;
    margin-bottom: 10px;
    font-size: 18px;
  }
</style>
</head>

<body>

  <div class="container">
    <h1 class="text-center mb-4">음성 입력 결과</h1>

    <!-- 📌 인식된 텍스트 표시 -->
    <c:if test="${not empty recognizedText}">
      <p class="recognized-text text-center">🎤 인식된 텍스트: <span>${recognizedText}</span></p>
    </c:if>

    <form action="${pageContext.request.contextPath}/fridge/saveVoiceResult" method="post">
      <table>
        <thead>
          <tr>
            <th>선택</th>
            <th>재료명 (수정 가능)</th>
            <th>수량</th>
            <th>단위</th>
            <th>유효기간</th>
          </tr>
        </thead>
        <tbody>
          <c:if test="${not empty parsedIngredients}">
            <c:forEach var="ingredient" items="${parsedIngredients}" varStatus="status">
              <tr>
                <td><input type="checkbox" name="selectedIndices" value="${status.index}"></td>
                <td><input type="text" class="form-control" name="name_${status.index}" value="${ingredient.name}"></td>
                <td><input type="number" class="form-control" name="quantity_${status.index}" value="${ingredient.quantity}" step="0.1"></td>
                <td><input type="text" class="form-control" name="unit_${status.index}" value="${ingredient.unit}"></td>
                <td><input type="date" class="form-control" name="expirationDate_${status.index}"></td>
              </tr>
            </c:forEach>
          </c:if>
          <c:if test="${empty parsedIngredients}">
            <tr>
              <td colspan="5">음성 데이터가 없습니다.</td>
            </tr>
          </c:if>
        </tbody>
      </table>

      <button type="submit" class="save-btn w-100">저장</button>
    </form>
  </div>

</body>
</html>
