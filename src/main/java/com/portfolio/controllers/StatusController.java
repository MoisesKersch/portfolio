package com.portfolio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.repositories.ProjetoRepository;
import com.portfolio.services.ProjetoService;

@RestController
public class StatusController
{
	@Autowired
	private ProjetoRepository projetoRepository;
	
	@Autowired
	private ProjetoService projetoService;
	
	@RequestMapping(value = "/changestatus", method = RequestMethod.POST)
	public void changeStatus()
	{
		projetoService.changeStatus(projetoRepository.findAll());
	}
}
