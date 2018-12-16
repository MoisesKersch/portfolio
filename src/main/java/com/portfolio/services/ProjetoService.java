package com.portfolio.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.portfolio.models.Projeto;

@Service
public interface ProjetoService 
{
	public Projeto save(Projeto projeto);
	public void changeStatus(List<Projeto> projeto);
	public Boolean remove(Projeto projeto);
}
