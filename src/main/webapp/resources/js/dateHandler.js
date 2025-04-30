document.addEventListener("DOMContentLoaded", function () {
  const today = new Date();
  const options = { year: 'numeric', month: 'long', day: 'numeric' };
  const formattedDate = today.toLocaleDateString('en-US', options);

  // 날짜를 표시할 span 태그에 동적으로 삽입
  const dateSpan1 = document.querySelector(".Today");
  const dateSpan2 = document.querySelector(".date");

  if (dateSpan1) {
    dateSpan1.innerText = "Today";
  }
  if (dateSpan2) {
    dateSpan2.innerText = `— ${formattedDate}`;
  }
});
