package com.portfolio.controllers;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.portfolio.component.SessaoInfo;
import com.portfolio.models.Role;
import com.portfolio.models.Usuario;
import com.portfolio.repositories.UsuarioRepository;
import com.portfolio.services.UsuarioService;

@Controller
@RequestMapping(value = "/public")
public class RegistroController extends SessaoInfo
{
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@RequestMapping(value = "/registro")
	public ModelAndView getRegistroPage()
	{
		ModelAndView modelAndView = new ModelAndView("registro");
		Usuario usuario = new Usuario();
		
		modelAndView.addObject("usuario", usuario);
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/registro", method = RequestMethod.POST)
	public String postRegistroPage(@Valid Usuario usuario, BindingResult bindingResult, HttpServletRequest request, final RedirectAttributes redirectAttributes)
	{
		if (bindingResult.hasErrors())
		{
			redirectAttributes.addFlashAttribute("mensagemErro", bindingResult.getAllErrors());
			return "redirect:registro";
		}

		// confere se o usuario existe
		Optional<Usuario> usuarioExiste = usuarioRepository.findByLogin(usuario.getLogin());
		
		if (usuarioExiste.isPresent())
		{
			redirectAttributes.addFlashAttribute("mensagemErro", "Login existente!");
			return "redirect:registro";
		}
		
		Set<Role> roles = new HashSet<Role>();
		Role role = new Role();
		role.setRole("Teste");
		roles.add(role);
		
		// selecion um papel
		usuario.setRoles(roles);
		
		// salva o usuario
		try
		{
			usuarioRepository.save(usuario);
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
			return null;
		}
		// logar apos o registro
		usuarioService.autoLogin(usuario.getLogin(), usuario.getSenha(), request);
		return "redirect:/home";
	}
}
