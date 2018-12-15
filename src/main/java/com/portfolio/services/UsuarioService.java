package com.portfolio.services;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.portfolio.models.Usuario;

@Service
public interface UsuarioService 
{
	public void salvar(Usuario usuario);
	public void remover(String id);
	public boolean autoLogin(String username, String password, HttpServletRequest request);
}
