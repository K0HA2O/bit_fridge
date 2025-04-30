package com.bit.mini.Controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HealthController {
	
	@Inject
	@Qualifier("healthCommand")
	private Command healthCommand;
	
	@Inject
	@Qualifier("registerBloodSugar")
	private Command registerBloodSugar;

	@RequestMapping(value="/health", method=RequestMethod.GET)
	public String health(HttpServletRequest request, Model model) {
		System.out.println("[HealthController] health()");
		
		model.addAttribute("request", request);
		healthCommand.execute(model);
		
		return "Myhealth/health";
	}
	
	@RequestMapping(value="/health/registerBloodSugar", method=RequestMethod.POST)
	public String registerBloodSugar(HttpServletRequest request, Model model) {
		System.out.println("[HealthController] registerBloodSugar()");
		
		model.addAttribute("request", request);
		registerBloodSugar.execute(model);
		
		return "redirect:/health";
	}
}
