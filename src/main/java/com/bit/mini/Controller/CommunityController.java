package com.bit.mini.Controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bit.mini.dao.CommunityDao;

@Controller
public class CommunityController {

	@Autowired
	private CommunityDao communityDao;

	@Inject
	@Qualifier("CommunityListCommand")
	private Command communityListCommand;

	@Inject
	@Qualifier("CommunityContentCommand")
	private Command communityContentCommand;

	@Inject
	@Qualifier("CommunityLikeCommand")
	private Command communityLikeCommand;

	@Inject
	@Qualifier("CommunityWriteCommand")
	private Command communityWriteCommand;

	@Inject
	@Qualifier("CommunityDeleteCommand")
	private Command communityDeleteCommand;

	@Inject
	@Qualifier("CommunityModifyCommand")
	private Command communityModifyCommand;

	@Inject
	@Qualifier("CommunityTopPostCommand")
	private Command communityTopPostCommand;

	@Inject
	@Qualifier("CommunityUserPostCommand")
	private Command communityUserPostCommand;
	
	
	

	@RequestMapping(value = "/community", method = RequestMethod.GET)
	public String communitytoppost(Model model) {

		communityTopPostCommand.execute(model);

		return "Community/Community";
	}

	
	// 게시글 상세보기
	@RequestMapping(value = "/showPost", method = RequestMethod.GET)
	public String showPost(HttpServletRequest request, Model model) {
	    // 요청 파라미터에서 게시글 ID 가져오기
	    String idParam = request.getParameter("id");       // 일반 게시글 상세보기
	    String postIdParam = request.getParameter("postId"); // 좋아요 상위 게시글 상세보기

	    // 적절한 게시글 ID 설정
	    int postId = (idParam != null) ? Integer.parseInt(idParam) : Integer.parseInt(postIdParam);

	    // Command 실행
	    model.addAttribute("request", request); // HttpServletRequest 전달
	    model.addAttribute("postId", postId);  // 게시글 ID 전달
	    communityContentCommand.execute(model);

	    // 게시글 상세보기 페이지로 이동
	    return "Community/CommunityContent";
	}

	
	
    
   
    
   
    
    
	@RequestMapping(value = "/userPosts", method = RequestMethod.GET)
	public String userPosts(@RequestParam("username") String username, Model model) {
	    // 디코딩된 사용자 이름 출력
	    System.out.println("Decoded Username: " + username);

	    // 사용자 이름을 Model에 추가
	    model.addAttribute("username", username);

	    // Command 실행
	    communityUserPostCommand.execute(model);

	    return "Community/CommunityUserPost";
	}



    
    
    
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Model model) {

		model.addAttribute("request", request);

		communityListCommand.execute(model);

		return "Community/CommunityList";
	}

	
	
	
	
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String writeView() {
		return "Community/CommunityWrite";
	}

	
	
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String writePost(@RequestParam("imagePath") MultipartFile imageFile, HttpServletRequest request,
			Model model) {
		model.addAttribute("request", request);
		model.addAttribute("imagePath", imageFile);
		communityWriteCommand.execute(model);
		return "redirect:./list";
	}

	
	
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public String edit(HttpServletRequest request, Model model) {
		model.addAttribute("request", request);

		try {
			communityContentCommand.execute(model);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "게시글을 불러오는데 실패했습니다.");
			return "Community/ErrorPage";
		}

		return "Community/CommunityModify";
	}



	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modifyPost(HttpServletRequest request,
			@RequestParam(value = "imagePath", required = false) MultipartFile imageFile, 
			Model model) {
		model.addAttribute("request", request);
		model.addAttribute("imagePath", imageFile); 

		try {
			communityModifyCommand.execute(model); // 수정 로직 처리
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "게시글 수정 중 오류가 발생했습니다.");
			return "Community/ErrorPage";
		}
		return "redirect:./list"; // 수정 완료 후 목록 페이지로 리다이렉트
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(HttpServletRequest request, Model model) {

		model.addAttribute("request", request);

		communityDeleteCommand.execute(model);

		return "redirect:./list";
	}

	@RequestMapping(value = "/likePost", method = RequestMethod.POST)
	public String likePost(@RequestParam(value = "postId", required = false) Integer postId,
			@RequestParam(value = "userId", required = false) Integer userId, Model model) {
		if (postId == null || postId <= 0) {
			throw new IllegalArgumentException("postId가 유효하지 않습니다.");
		}

		if (userId == null || userId <= 0) {
			throw new IllegalArgumentException("userId가 유효하지 않습니다.");
		}

		model.addAttribute("postId", postId);
		model.addAttribute("userId", userId);
		communityLikeCommand.execute(model);

		return "redirect:/list";
	}

	@RequestMapping("../showPost")
	public String showPost(@RequestParam(value = "postId", required = false) Integer postId, Model model) {
		model.addAttribute("postId", postId);
		return "Community/CommunityContent";
	}
	
	@RequestMapping(value="/ContentlikePost", method = RequestMethod.POST)
	public String ContentlikePost(@RequestParam(value="postId", required=false) Integer postId,
			@RequestParam(value="userId", required = false) Integer userId, Model model) {
		if (postId == null || postId <= 0) {
			throw new IllegalArgumentException("postId 어쩌구");
		}
		
		if (userId == null || userId <= 0) {
			throw new IllegalArgumentException("userId 어쩌구");
		}
		
		model.addAttribute("postId", postId);
		model.addAttribute("userId", userId);
		communityLikeCommand.execute(model);

		return "redirect:/showPost";
	}

	
	
	
	
	
	
	

}
