package com.portfolio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.models.Role;

public interface EnderecoRepository extends JpaRepository<Role, Long>
{
	
}
