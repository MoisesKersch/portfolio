package com.portfolio.pojo;

public class Risco
{
	private String descricao;
	private Boolean status;

	public Risco(String descricao, Boolean status)
	{
		this.descricao = descricao;
		this.status = status;
	}
	
	public String getDescricao()
	{
		return descricao;
	}

	public void setDescricao(String descricao)
	{
		this.descricao = descricao;
	}

	public Boolean getStatus()
	{
		return status;
	}

	public void setStatus(Boolean status)
	{
		this.status = status;
	}
}
