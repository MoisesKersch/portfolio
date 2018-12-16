package com.portfolio.pojo;

import java.util.ArrayList;
import java.util.List;

public class Causa
{
	private List<String> causas;

	public Causa()
	{
		causas = new ArrayList<>();
		causas.add("Metas e objetivos mal estabelecidos");
		causas.add("Falta de conhecimento de pontos chaves do projeto");
		causas.add("Tempo insuficiente para o planejamento do projeto");
		causas.add("Falta de coordenação entre as partes");
		causas.add("Os envolvidos não possuem habilidades");
		causas.add("Expectativas não alinhadas com a realidade do projeto");
		causas.add("Objetivos mudaram com o andamento do projeto");
		causas.add("Falta de participação da equipe na tomada de decisões");
		causas.add("O treinamento e capacitação inadequados");
		causas.add("Papéis e responsabilidades mal definidos");
		causas.add("Requisitos inadequados ou vagos");
		causas.add("Riscos elevados no meio ambiente");
		causas.add("Mudança na estrutura organizacional da empresa");
		causas.add("Prazos e tarefas irrealistas");
		causas.add("Mudanças na tecnologia disponível");
		causas.add("Falha de comunicação");
		causas.add("Insuficiência de recursos");
		causas.add("Evolução nos preços e prazos");
		causas.add("Cenário político-econômico desfavorável");
		causas.add("As estimativas de custo e cronograma são errôneas");
		causas.add("Pouca compreensão da complexidade do projeto");
		causas.add("O sistema de controle inadequado");
		causas.add("Falta de liderança do gerente de projeto");
		causas.add("As estimativas financeiras são pobres e incompletas");
		causas.add("Os sinais de alerta do projeto foram ignorados");
		causas.add("Falha no controle de desempenho");
		causas.add("Falta de motivação do time e dos interessados");
		causas.add("Falta de apoio dentro da organização");
		causas.add("Falta de entendimento do escopo do projeto");
		causas.add("Scope creep – Crescimento desordenado do escopo");
		causas.add("Falta de patrocínio");
		causas.add("Excesso de otimismo");
		causas.add("Pensar que o planejamento é perda de tempo");
		causas.add("Processo de gestão de mudança inexistente ou pobre");
		causas.add("Recursos inadequados");
	}

	public List<String> getCausas()
	{
		return causas;
	}

	public void setCausas(List<String> causas)
	{
		this.causas = causas;
	}
}