package com.portfolio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.models.Projeto;

public interface ProjetoRepository extends JpaRepository<Projeto, Long>
{
}
