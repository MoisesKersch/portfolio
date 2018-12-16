package com.portfolio.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.annotation.JsonAppend.Prop;
import com.portfolio.models.Projeto;
import com.portfolio.pojo.Causa;
import com.portfolio.pojo.Risco;
import com.portfolio.repositories.ProjetoRepository;

@Service
public class ProjetoServiceImpl implements ProjetoService
{
	@Autowired
	private ProjetoRepository projetoRepository;

	@Override
	public Projeto save(Projeto projeto)
	{
		if ( projeto.getId() == null )
		{
			projeto.setRisco(getStatus(gerarRiscos()));
			
			if (!projeto.getRisco().equals(""))
			{
				projeto.setStatus("Em análise");
			}
			else
				projeto.setStatus("Cancelado");
		}
		
		return projetoRepository.save(projeto);
	}

	public String getStatus(Integer r)
	{
		if (r >= 0 && r < 15) {
			return "Baixo";
		}
		if (r >= 12 && r < 20) {
			return "Médio";
		}
		if (r >= 18 && r < 27) {
			return "Alto";
		}
		return "";
	}

	public Integer gerarRiscos()
	{
		int size = 0;
		List<Risco> riscos = new ArrayList<>();
		Causa causa = new Causa();

		for (String x : causa.getCausas()) {
			riscos.add(new Risco(x, getRandomBoolean()));
		}
		for (Risco x : riscos) {
			if (x.getStatus())
				size++;
		}
		return size;
	}

	public boolean getRandomBoolean()
	{
		Random random = new Random();
		return random.nextBoolean();
	}

	public Integer getRandomPercentage()
	{
		Random rand = new Random();
		return rand.nextInt(100);
	}

	@Override
	public void changeStatus(List<Projeto> projetos)
	{
		for (Projeto x : projetos) 
		{
			if (!x.getStatus().equals("Cancelado") && !x.getStatus().equals("Encerrado"))
			{
				int r = gerarRiscos();
				int indexOf = getStatus().indexOf(x.getStatus());

 				if (getRandomPercentage() > 50 && indexOf < getStatus().size()) 
				{
					x.setStatus(getStatus().get(indexOf + 1));
				}
				else if (r > 22)
				{
					x.setStatus("Cancelado");
				}
				projetoRepository.save(x);
			}
			continue;
		}
	}

	public List<String> getStatus()
	{
		List<String> status = new ArrayList<>();
		status.add("Em análise");
		status.add("Análise Realizada");
		status.add("Análise Aprovada");
		status.add("Iniciado");
		status.add("Planejado");
		status.add("Em Andamento");
		status.add("Encerrado");
		status.add("Cancelado");
		return status;
	}

	@Override
	public Boolean remove(Projeto projeto)
	{
		try {

			if (projeto.getStatus().equals("Iniciado") || projeto.getStatus().equals("Em Andamento")
					|| projeto.getStatus().equals("Encerrado")) {
				return false;
			}
			projetoRepository.deleteById(projeto.getId());
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
