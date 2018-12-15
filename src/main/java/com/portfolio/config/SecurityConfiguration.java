package com.portfolio.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.portfolio.repositories.UsuarioRepository;
import com.portfolio.services.UsuarioDetailsServiceImpl;

// configuração referente ao repositorio do usuario
@EnableJpaRepositories(basePackageClasses = UsuarioRepository.class)
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
	@Autowired
	private UsuarioDetailsServiceImpl usuarioDetailsServiceImpl;

	// sobreresscrevendo o metodo de construtor de autentificação
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		// uma instancia dessa classe possui varios metodos de autentificação, como "em
		// memoria", "direto do banco"...
		// vamos utilizar o metodo de autentificação via "serviço" esse serviço vai se
		// conectar com a base de dados e obter data
		auth.userDetailsService(usuarioDetailsServiceImpl).passwordEncoder(getPasswordEncoder());
	}

	@Bean(name = "authenticationManager")
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception
	{
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception
	{

		http.authorizeRequests().antMatchers("/resources/**", "/public/**", "/css/**").permitAll()
				.antMatchers("/admin/**").hasRole("Administrator").and().authorizeRequests().anyRequest()
				.authenticated().and().formLogin().loginPage("/login").defaultSuccessUrl("/home", true)
				.passwordParameter("senha").usernameParameter("login").permitAll().and().logout().and()
				.exceptionHandling().accessDeniedPage("/login");

		http.csrf().disable();
	}

	private PasswordEncoder getPasswordEncoder()
	{
		return new PasswordEncoder()
		{
			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword)
			{
				return rawPassword.toString().equals(encodedPassword);
			}

			@Override
			public String encode(CharSequence rawPassword)
			{
				return rawPassword.toString();
			}
		};
	}

	@Bean
	public AuthenticationTrustResolver trustResolver()
	{
		return new AuthenticationTrustResolver()
		{

			@Override
			public boolean isRememberMe(final Authentication authentication)
			{
				return false;
			}

			@Override
			public boolean isAnonymous(final Authentication authentication)
			{
				return false;
			}
		};
	}
}
