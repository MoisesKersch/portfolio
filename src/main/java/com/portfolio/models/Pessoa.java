package com.portfolio.models;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

@Table(name = "pessoa")
@Entity
public class Pessoa
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pessoa_id")
	private Long id;
	
	@NotNull
	@Column(length = 100)
	private String nome;
	
	@Column(length = 100)
	private Date dataNascimento;
	
	@Column(length = 14)
	private String cpf;
	
	private Boolean funcionario = false;
	
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(name = "membro", joinColumns = @JoinColumn(name = "pessoa_id"), inverseJoinColumns = @JoinColumn(name = "projeto_id"))
	private Set<Projeto> projeto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Boolean getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Boolean funcionario) {
		this.funcionario = funcionario;
	}

	public Set<Projeto> getProjeto()
	{
		return projeto;
	}

	public void setProjeto(Set<Projeto> projeto)
	{
		this.projeto = projeto;
	}
}