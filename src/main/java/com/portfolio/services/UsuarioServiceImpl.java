package com.portfolio.services;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import com.portfolio.models.Usuario;
import com.portfolio.repositories.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService
{

	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	AuthenticationManager authManager;

	@Override
	public void salvar(Usuario usuario)
	{
		try
		{
			usuarioRepository.save(usuario);
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void remover(String id)
	{
	}

	@Override
	public boolean autoLogin(String username, String password, HttpServletRequest request)
	{
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

		Authentication authentication = authManager.authenticate(token);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		// this step is important, otherwise the new login is not in session which is
		// required by Spring Security
		request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
				SecurityContextHolder.getContext());

		return true;
	}
}
