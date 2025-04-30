package com.bit.mini.Controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CalendarController {
	
	@Inject
	@Qualifier("addRecipeToCalendarCommand")
	private Command addRecipeToCalendarCommand;
	
	@Inject
	@Qualifier("calendarCommand")
	private Command calendarCommand;
	
	@Inject
	@Qualifier("deleteCommand")
	private Command deleteCommand;
	
	@Inject
	@Qualifier("updateCommand")
	private Command updateCommand;
	
	@RequestMapping(value="/calendar/add", method=RequestMethod.POST)
	public String addRecipeToCalendar(HttpServletRequest request, Model model) {
		System.out.println("addRecipeToCalendar()");
		
		model.addAttribute("request", request);
		addRecipeToCalendarCommand.execute(model);
		
		return "redirect:/calendar";
	}
	
	@RequestMapping(value="/calendar", method=RequestMethod.GET)
	public String calendar(HttpServletRequest request, Model model) {
		System.out.println("calendar()");

		model.addAttribute("request", request);
		calendarCommand.execute(model);
		
		return "MyCalendar/calendar";
	}
	
	@RequestMapping(value="/calendar/delete", method=RequestMethod.GET)
	public String delete(HttpServletRequest request, Model model) {
		System.out.println("delete()");
		
		model.addAttribute("request", request);
		deleteCommand.execute(model);
		
		return "redirect:/calendar";
	}

	@RequestMapping(value="/calendar/update", method=RequestMethod.POST)
	public String updateEventDate(HttpServletRequest request, Model model) {
	    System.out.println("updateEventDate()");

	    model.addAttribute("request", request);
	    updateCommand.execute(model);

	    return "redirect:/calendar";
	}
}
