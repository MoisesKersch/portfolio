package com.portfolio.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "endereco")
public class Endereco
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "endereco_id")
	private Long id;
	
	@Column(name = "rua")
	private String rua;
	
	@Column(name = "numero")
	private Long numero;
	
	@Column(name = "complemento")
	private String complemento;

	@Column(name = "bairro")
	private String bairro;

	@Column(name = "cidade")
	private String cidade;

	@Column(name = "uf")
	private String uf;

	public Endereco()
	{
		
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getBairro()
	{
		return bairro;
	}

	public void setBairro(String bairro)
	{
		this.bairro = bairro;
	}

	public String getCidade()
	{
		return cidade;
	}

	public void setCidade(String cidade)
	{
		this.cidade = cidade;
	}

	public String getUf()
	{
		return uf;
	}

	public void setUf(String uf)
	{
		this.uf = uf;
	}

	public String getComplemento()
	{
		return complemento;
	}

	public void setComplemento(String complemento)
	{
		this.complemento = complemento;
	}

	public String getRua()
	{
		return rua;
	}

	public void setRua(String rua)
	{
		this.rua = rua;
	}

	public Long getNumero()
	{
		return numero;
	}

	public void setNumero(Long numero)
	{
		this.numero = numero;
	}
}