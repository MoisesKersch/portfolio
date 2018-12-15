package com.portfolio.models;

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

@Entity
@Table(name = "usuario")
public class Usuario
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "usuario_id")
	private Long id;
	
	@NotNull
	@Column(name = "login", unique = true)
	private String login;
	
	@NotNull
	@Column(name = "senha")
	private String senha;
	
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;
	
//	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	@Fetch(value = FetchMode.SUBSELECT)
//	@JoinTable(name = "usuario_endereco", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "endereco_id"))
//	private List<Endereco> enderecos = new ArrayList<Endereco>();
	
	public Usuario()
	{
		
	}

	public Usuario(Usuario usuario)
	{
		this.roles = usuario.getRoles();
		this.id = usuario.getId();
		this.senha = usuario.getSenha();
		this.login = usuario.getLogin();
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getSenha()
	{
		return senha;
	}

	public void setSenha(String senha)
	{
		this.senha = senha;
	}

	public Set<Role> getRoles()
	{
		return roles;
	}

	public String getLogin()
	{
		return login;
	}

	public void setLogin(String login)
	{
		this.login = login;
	}

	public void setRoles(Set<Role> roles)
	{
		this.roles = roles;
	}

//	public List<Endereco> getEnderecos()
//	{
//		return enderecos;
//	}
//
//	public void setEndereco(List<Endereco> enderecos)
//	{
//		this.enderecos = enderecos;
//	}
//
//	public void setEnderecos(List<Endereco> enderecos)
//	{
//		this.enderecos = enderecos;
//	}
}