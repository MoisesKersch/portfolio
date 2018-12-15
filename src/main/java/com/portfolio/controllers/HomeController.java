package com.portfolio.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.portfolio.component.SessaoInfo;

@Controller
public class HomeController extends SessaoInfo
{

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView home(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView modelAndView = new ModelAndView("home");

		if (getUsuarioCorrente() == null)
			return new ModelAndView("/login");

		modelAndView.addObject("login",  getUsuarioCorrente().getLogin());
		modelAndView.addObject("page", "Home");
		return modelAndView;
	}
}
