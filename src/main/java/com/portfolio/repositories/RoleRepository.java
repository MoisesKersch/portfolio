package com.portfolio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long>
{
	public Role findByRole(String role);
}
