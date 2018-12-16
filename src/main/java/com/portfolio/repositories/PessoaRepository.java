package com.portfolio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.models.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>
{
}
