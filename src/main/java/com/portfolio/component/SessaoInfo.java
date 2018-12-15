package com.portfolio.component;

import org.springframework.security.core.context.SecurityContextHolder;

import com.portfolio.models.Usuario;

public abstract class SessaoInfo
{
	protected Usuario getUsuarioCorrente()
	{
		try {
			Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if (usuario != null)
				if (usuario.getLogin() == null) 
					return null;
			return usuario;
		} catch (Exception e) 
		{
			return null;
		}
	}
}