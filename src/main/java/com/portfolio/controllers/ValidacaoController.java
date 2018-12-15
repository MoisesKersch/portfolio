package com.portfolio.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.models.Usuario;
import com.portfolio.repositories.UsuarioRepository;
import com.portfolio.utils.documentosPessoais.CNP;

// nesta classe escrever codigos que precisam ser validado
@RestController
@RequestMapping(value = "/public")
public class ValidacaoController
{
	@Autowired
	UsuarioRepository usuarioRepository;

	@RequestMapping(value = "/iscpfcnpjvalido", method = RequestMethod.POST)
	public Boolean isCpfCnpjValido(String entrada)
	{
		try {
			return CNP.validaCPF(entrada);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
		
	@RequestMapping(value = "/isusuarioexiste", method = RequestMethod.POST)
	public Boolean isUsuarioExiste(String login)
	{
		Optional<Usuario> usuarioExiste = usuarioRepository.findByLogin(login);
		return usuarioExiste.isPresent();
	}
}
