package com.portfolio.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table(name = "membro")
@Entity
public class Membro implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@ManyToOne
	@JoinColumn
	private Projeto idprojeto;

	@NotNull
	@ManyToOne
	@JoinColumn
	private Pessoa idpessoa;

	public Projeto getIdprojeto() {
		return idprojeto;
	}

	public void setIdprojeto(Projeto idprojeto) {
		this.idprojeto = idprojeto;
	}

	public Pessoa getIdpessoa() {
		return idpessoa;
	}

	public void setIdpessoa(Pessoa idpessoa) {
		this.idpessoa = idpessoa;
	}

}