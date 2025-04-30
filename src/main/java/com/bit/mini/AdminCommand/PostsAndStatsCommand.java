package com.bit.mini.AdminCommand;


import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bit.mini.Controller.AdminCommand;
import com.bit.mini.dao.AdminDao;
import com.google.gson.Gson;



// PostList.jsp 에서 월별 게시글 리스트 월별 사용자 통계
@Component("PostsAndStatsCommand")
public class PostsAndStatsCommand implements AdminCommand {
	
	

	@Autowired
    private AdminDao adminDao;

	// execute 메서드: fetchData를 호출하고 JSON 응답 생성
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            // fetchData 호출
            Object data = fetchData(request, response);

            // JSON 응답 작성
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out = response.getWriter();
            Gson gson = new Gson();
            out.print(gson.toJson(data)); // 데이터를 JSON 형식으로 변환 후 응답
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    // fetchData 메서드: 데이터를 처리하고 반환
    public Object fetchData(HttpServletRequest request, HttpServletResponse response) {
        String yearParam = request.getParameter("year");
        String monthParam = request.getParameter("month");
        int year = (yearParam != null) ? Integer.parseInt(yearParam) : java.time.LocalDate.now().getYear();
        int month = (monthParam != null) ? Integer.parseInt(monthParam) : java.time.LocalDate.now().getMonthValue();

        // DAO 호출 및 결과 데이터 가공
        Map<String, Object> result = new HashMap<>();
        // 게시글 데이터
        result.put("posts", adminDao.getPostsByYearAndMonth(year, month)); 
        // 주별 통계 데이터
        result.put("stats", adminDao.getWeeklyPostStats(year, month));    

        return result; 
    }

}
