package com.portfolio.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.portfolio.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>
{
	Optional<Usuario> findByLogin(String login);
	
	@Query(value = " select u.usuario_id, ativo, cpf, email, login, nome, senha from usuario u " + 
			       " join usuario_empresa eu " + 
			       " on u.usuario_id = eu.usuario_id " + 
			       " where u.login = :login"+ 
			       " and eu.empresa_id = :empresaId ", nativeQuery = true)          
	public Optional<Usuario> findByLoginAndEmpresaId( @Param("login")String login, @Param("empresaId") Long empresaId );
	
	@Query(value = " select u.usuario_id, ativo, cpf, email, login, nome, senha from usuario u " + 
		           " join usuario_empresa eu " + 
		           " on u.usuario_id = eu.usuario_id " + 
		           " where eu.empresa_id = :empresaId ", nativeQuery = true)          
	public List<Usuario> findByEmpresaId(@Param("empresaId") Long empresaId );
	
	@Query(value = 	" select u.usuario_id, ativo, cpf, email, login, nome, senha, empresa_id" + 
					" from usuario u " + 
					" join user_role ur " + 
					" on u.usuario_id = ur.usuario_id " + 
					" where u.empresa_id = :empresaId and ur.role_id <> 1 ", nativeQuery = true)          
	public List<Usuario> findByEmpresaIdRoleAdminNot(@Param("empresaId") Long empresaId );
	
}