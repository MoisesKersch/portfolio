package com.portfolio.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.portfolio.models.Pessoa;
import com.portfolio.models.Projeto;
import com.portfolio.repositories.PessoaRepository;
import com.portfolio.repositories.ProjetoRepository;

@RestController
@RequestMapping(value = "/public")
public class MembroController
{
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private ProjetoRepository projetoRepository;
	
	@RequestMapping(value = "/membro", method = RequestMethod.POST)
	public String pessoaPost(@Valid Pessoa pessoa, BindingResult bindingResult, Long projeto, HttpServletRequest request, final RedirectAttributes redirectAttributes)
	{
		if (bindingResult.hasErrors())
		{
			if (pessoa.getFuncionario() == true)
			{
				 Set<Projeto> projetos = new HashSet<Projeto>(); 
				 Projeto projetoTempt = projetoRepository.findById(projeto).get();
				 projetos.add(projetoTempt);
				 pessoa.setProjeto( projetos );
			}else
				return "Erro";
		}
		
		try
		{
			
			pessoa = pessoaRepository.save(pessoa);
		} catch (Exception e)
		{
			return e.getMessage();
		}
		
		return "Cadastro Realizado com Sucesso!";
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
}
