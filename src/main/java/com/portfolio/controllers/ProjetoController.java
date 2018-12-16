package com.portfolio.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.portfolio.component.SessaoInfo;
import com.portfolio.models.Pessoa;
import com.portfolio.models.Projeto;
import com.portfolio.repositories.PessoaRepository;
import com.portfolio.repositories.ProjetoRepository;
import com.portfolio.services.ProjetoService;

@Controller
public class ProjetoController extends SessaoInfo
{
	@Autowired
	private ProjetoRepository projetoRepository;
	
	@Autowired
	private ProjetoService projetoService;

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@RequestMapping(value = "/projeto", method = RequestMethod.GET)
	public ModelAndView projeto(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView modelAndView = new ModelAndView("projeto");

		if (getUsuarioCorrente() == null)
			return new ModelAndView("/login");

		modelAndView.addObject("login",  getUsuarioCorrente().getLogin());
		modelAndView.addObject("page", "Projeto");
		modelAndView.addObject("title", "Cadastro de Projetos");
		modelAndView.addObject("subTitle", "Cadastro de Projetos");
		modelAndView.addObject("caption", "Cadastre e atualize seus projetos");
		modelAndView.addObject("tableTitle", "Tabela de projetos cadastrados");
		modelAndView.addObject("tableId", "projeto-table");
		modelAndView.addObject("js", "projeto.js");
		modelAndView.addObject("jsEditor", "projeto-editor.js");
		modelAndView.addObject("modalTitle", "Cadastrar");
		modelAndView.addObject("formId", "projeto-form");
		modelAndView.addObject("modalId", "projeto-modal");
		
		return modelAndView;
	}
	
	@ResponseBody
 	@RequestMapping(value = "/getprojetos")
	public List<Projeto> getProjetos()
	{
		try 
		{
			return projetoRepository.findAll();
		} catch (Exception e) {
			return null;
		}
	}
	
	@ResponseBody
 	@RequestMapping(value = "/getpessoasfromprojeto")
	public List<Pessoa> getPessoas()
	{
		try 
		{
			return pessoaRepository.findAll();
		} catch (Exception e) {
			return null;
		}
	}
	
	@RequestMapping(value = "/projeto", method = RequestMethod.POST)
	public String projetoPost(@Valid Projeto projeto, BindingResult bindingResult, Long idGerente, HttpServletRequest request, final RedirectAttributes redirectAttributes)
	{
		if (bindingResult.hasErrors())
		{
			
		}
		
		try
		{
			projeto.setIdGerente(pessoaRepository.findById(idGerente).get());
			projeto = projetoService.save(projeto);
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
			return null;
		}
		return "redirect:/getprojetos";
	}

	@ResponseBody
	@RequestMapping(value = "/projetoremove", method = RequestMethod.POST)
	public Boolean removerProjeto(Long id)
	{
		try 
		{
			return projetoService.remove(projetoRepository.findById(id).get());
		} catch (Exception e) {
			return false;
		}
	}
}
