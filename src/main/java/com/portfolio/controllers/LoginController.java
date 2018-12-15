package com.portfolio.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController
{
	@RequestMapping(value = {"/login", "/"})
	public ModelAndView getLoginPage()
	{
		ModelAndView modelAndView = new ModelAndView("login");
		modelAndView.addObject("page", "Login");
		return modelAndView;
	}
}