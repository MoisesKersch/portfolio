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
import com.portfolio.repositories.PessoaRepository;

@Controller
public class PessoaController extends SessaoInfo
{
	@Autowired
	private PessoaRepository pessoaRepository;

	@RequestMapping(value = "/pessoa", method = RequestMethod.GET)
	public ModelAndView pessoa(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView modelAndView = new ModelAndView("pessoa");

		if (getUsuarioCorrente() == null)
			return new ModelAndView("/login");

		modelAndView.addObject("login",  getUsuarioCorrente().getLogin());
		modelAndView.addObject("page", "Pessoa");
		modelAndView.addObject("title", "Cadastro de Pessoas");
		modelAndView.addObject("subTitle", "Cadastro de Pessoas");
		modelAndView.addObject("caption", "Cadastre e atualize pessoas");
		modelAndView.addObject("tableTitle", "Tabela de pessoas cadastradas");
		modelAndView.addObject("tableId", "pessoa-table");
		modelAndView.addObject("js", "pessoa.js");
		modelAndView.addObject("jsEditor", "pessoa-editor.js");
		modelAndView.addObject("modalTitle", "Cadastrar");
		modelAndView.addObject("formId", "pessoa-form");
		modelAndView.addObject("modalId", "pessoa-modal");
		return modelAndView;
	}
	
	@ResponseBody
 	@RequestMapping(value = "/getpessoas")
	public List<Pessoa> getPessoas()
	{
		try 
		{
			return pessoaRepository.findAll();
		} catch (Exception e) {
			return null;
		}
	}
	
	@RequestMapping(value = "/pessoa", method = RequestMethod.POST)
	public String pessoaPost(@Valid Pessoa pessoa, BindingResult bindingResult, HttpServletRequest request, final RedirectAttributes redirectAttributes)
	{
		if (bindingResult.hasErrors())
		{
			//
			redirectAttributes.addFlashAttribute("mensagemErro", bindingResult.getAllErrors());
			return null;
		}
		
		try
		{
			if (pessoa.getFuncionario() == null)
				pessoa.setFuncionario(false);
			pessoa = pessoaRepository.save(pessoa);
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
			return null;
		}
		return "redirect:/getpessoas";
	}

	@ResponseBody
	@RequestMapping(value = "/pessoaremove", method = RequestMethod.POST)
	public Boolean removerPessoa(Long id)
	{
		try 
		{
			pessoaRepository.deleteById(id);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
