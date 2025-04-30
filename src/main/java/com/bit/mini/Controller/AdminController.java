package com.bit.mini.Controller;


import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.bit.mini.dao.AdminDao;
import com.bit.mini.dto.CommunityPostDto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
public class AdminController {

	@Autowired
    private AdminDao adminDao;	
	
	@Inject
    @Qualifier("AdminPageCommand")
    private AdminCommand adminPageCommand;

	@Inject
    @Qualifier("ViewUserListCommand")
    private AdminCommand viewUserListCommand;

	@Inject
    @Qualifier("ViewUserDetailCommand")
    private AdminCommand viewUserDetailCommand;
	
	
	@Inject
    @Qualifier("UserDeleteCommand")
    private AdminCommand userDeleteCommand;
	
	@Inject
    @Qualifier("PostDeleteCommand")
    private AdminCommand postDeleteCommand;
	
	
	
	//이새끼 문제있음
//	@Inject
//	@Qualifier("PostsAndStatsCommand")
//	private AdminCommand postsAndStatsCommand;
	
	
	@Inject
	@Qualifier("ManageAdCommand")
	private AdminCommand manageAdCommand;
	
	
	@Inject
	@Qualifier("AddAdCommand")
	private AdminCommand addAdCommand;
	
	
	@Inject
	@Qualifier("DeleteAdCommand")
	private AdminCommand deleteAdCommand;

	
	
	@Inject
	@Qualifier("RandomAdCommand")
	private AdminCommand ranomAdCommand;
	
	
	
	
	
	
	
	// 관리자 메인 페이지
    @RequestMapping("/adminPage")
    public String adminPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
        adminPageCommand.execute(request, response); 
        
        return "Admin/Admin"; 
    }

    
    
    
    
    // 회원 목록 페이지
    @RequestMapping("/viewUserList")
    public String viewUserList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	
        viewUserListCommand.execute(request, response); 
        
        return "Admin/UserList"; 
    }

    
    
    
    
    // 회원 상세 정보 페이지
    @RequestMapping("/viewUserDetail")
    public String viewUserDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String idParam = request.getParameter("id");
        
        viewUserDetailCommand.execute(request, response);
        return "Admin/UserDetail"; 
    }

    
    @RequestMapping(value = "/deletePost", method = RequestMethod.GET)
    public String deletePost(HttpServletRequest request, HttpServletResponse response) {
        postDeleteCommand.execute(request, response);

        
        return "redirect:./managePosts";
    }

    
    @RequestMapping(value = "/manageAds", method = RequestMethod.GET)
    public String manageAds(HttpServletRequest request, HttpServletResponse response) {
        manageAdCommand.execute(request, response);
        return "Admin/ManageAds"; 
    }

    @RequestMapping(value = "/addAd", method = RequestMethod.GET)
    public String showAddAdPage(HttpServletRequest request, HttpServletResponse response) {
        return "Admin/AddAd"; 
    }

    @RequestMapping(value = "/addAdProcess", method = RequestMethod.POST)
    public String addAd(@RequestParam("imagePath") MultipartFile imageFile, HttpServletRequest request, HttpServletResponse response) {
       
        request.setAttribute("imagePath", imageFile);

        
        addAdCommand.execute(request, response);

        // 광고 관리 페이지로 리다이렉트
        return "redirect:./manageAds";
    }


    @RequestMapping(value = "/deleteAd", method = RequestMethod.GET)
    public String deleteAd(HttpServletRequest request, HttpServletResponse response) {
        deleteAdCommand.execute(request, response);
        return "redirect:./manageAds"; // 광고 관리 페이지로 리다이렉트
    }
    
    @RequestMapping(value = "/postsAndStats")
    public ModelAndView postsAndStats(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("[DEBUG] AdminController - postsAndStats 호출됨");

        // 요청된 년도, 월 가져오기 (폼에서 전송한 name과 동일해야 합니다)
        String yearParam = request.getParameter("year");
        String monthParam = request.getParameter("month");

        int year = (yearParam != null) ? Integer.parseInt(yearParam) : java.time.LocalDate.now().getYear();
        int month = (monthParam != null) ? Integer.parseInt(monthParam) : java.time.LocalDate.now().getMonthValue();

        System.out.println("[DEBUG] 요청된 year: " + year + ", month: " + month);

        // DAO에서 데이터 가져오기
        List<CommunityPostDto> postList = adminDao.getPostsByYearAndMonth(year, month);
        List<Map<String, Object>> statsList = adminDao.getWeeklyPostStats(year, month);

        // Gson을 사용하여 statsList를 JSON 문자열로 변환
        Gson gson = new GsonBuilder().create();
        String statsJson = (statsList != null && !statsList.isEmpty()) ? gson.toJson(statsList) : "[]";

        System.out.println("[DEBUG] 변환된 statsJson: " + statsJson);

        // ModelAndView 객체 생성
        ModelAndView mav = new ModelAndView();
        mav.setViewName("Admin/Admin");  // JSP 경로
        mav.addObject("posts", postList);   // 게시글 리스트
        mav.addObject("statsJson", statsJson);  // 주별 통계 리스트

        return mav;
    }


    

}

