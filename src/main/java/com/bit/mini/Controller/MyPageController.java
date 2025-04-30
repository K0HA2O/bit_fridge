package com.bit.mini.Controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MyPageController {
	
	@Inject
	@Qualifier("addMyRecipeCommand")
	private Command addMyRecipeCommand;
	
	@Inject
	@Qualifier("myRecipeListCommand")
	private Command myRecipeListCommand;
	
	@Inject
	@Qualifier("deleteMyRecipeCommand")
	private Command deleteMyRecipeCommand;
	
	@Inject
	@Qualifier("myPostListCommand")
	private Command myPostListCommand;
	
	@Inject
	@Qualifier("deleteMyPostCommand")
	private Command deleteMyPostCommand;
	
	@Inject
	@Qualifier("myInfoCommand")
	private Command myInfoCommand;
	
	@Inject
	@Qualifier("infoModifyCommand")
	private Command infoModifyCommand;
	
	@RequestMapping(value="/myPage", method=RequestMethod.GET)
	public String myPage() {
		System.out.println("myPage()");
		
		return "MyPage/myPage";
	}
	
	@RequestMapping(value="/myPage/addMyRecipe", method=RequestMethod.POST)
	public String addMyRecipe(HttpServletRequest request, Model model) {
		System.out.println("addMyRecipeList()");
		
		model.addAttribute("request", request);
		addMyRecipeCommand.execute(model);
		
		return "redirect:/recipe";
	}
	
	@RequestMapping(value="/myPage/myRecipeList", method=RequestMethod.GET)
	public String myRecipeList(HttpServletRequest request, Model model) {
		System.out.println("myRecipeList()");
		
		model.addAttribute("request", request);
		myRecipeListCommand.execute(model);
		
		return "MyPage/myRecipeList";
	}
	
	@RequestMapping(value="/myPage/deleteMyRecipe", method=RequestMethod.POST)
	public String deleteMyRecipe(HttpServletRequest request, Model model) {
		System.out.println("deleteMyRecipe()");
		
		model.addAttribute("request", request);
		deleteMyRecipeCommand.execute(model);
		
		return "redirect:/myPage/myRecipeList";
	}
	
	@RequestMapping(value="/myPage/myPostList", method=RequestMethod.GET)
	public String myPostList(HttpServletRequest request, Model model) {
		System.out.println("myPostList()");
		
		model.addAttribute("request", request);
		myPostListCommand.execute(model);
		
		return "MyPage/myPostList";
	}
	
	@RequestMapping(value="/myPage/deleteMyPost", method=RequestMethod.POST)
	public String deleteMyPost(HttpServletRequest request, Model model) {
		System.out.println("deleteMyPost()");
		
		model.addAttribute("request", request);
		deleteMyPostCommand.execute(model);
		
		return "redirect:/myPage/myPostList";
	}
	
	@RequestMapping(value="/myPage/myInfo", method=RequestMethod.GET)
	public String myInfo(HttpServletRequest request, Model model) {
		System.out.println("myInfo()");
		
		model.addAttribute("request", request);
		myInfoCommand.execute(model);
		
		return "MyPage/myInfo";
	}
	
	@RequestMapping(value="/myPage/infoModify", method=RequestMethod.POST)
	public String infoModify(HttpServletRequest request, Model model) {
		System.out.println("infoModify()");
		
		model.addAttribute("request", request);
		infoModifyCommand.execute(model);
		
		return "redirect:/myPage";
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout(HttpSession session) {
		System.out.println("logout()");
		
		session.invalidate();

		return "redirect:/home";
	}
}
