package com.portfolio.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.portfolio.models.CustomUsersDetails;
import com.portfolio.models.Usuario;
import com.portfolio.repositories.UsuarioRepository;

@Service
public class UsuarioDetailsServiceImpl implements UserDetailsService
{
	@Autowired
	UsuarioRepository usersRepository; 
	
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException
	{
		Optional<Usuario> users = usersRepository.findByLogin(login);
		
		users.orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado"));
		
		return users.map(CustomUsersDetails::new).get();
	}
}
