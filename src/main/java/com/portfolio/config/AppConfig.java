package com.portfolio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class AppConfig extends WebMvcConfigurationSupport
{
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer)
	{
		configurer.enable();
	}

	@Override
	public void addCorsMappings(CorsRegistry registry)
	{
		registry.addMapping("/**").allowedMethods("PUT", "GET", "DELETE", "OPTIONS", "PATCH", "POST");
	}

	@Bean
	public BCryptPasswordEncoder passwordEnconder()
	{
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

	@Bean
	public InternalResourceViewResolver viewResolver()
	{
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/pages/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	private static final String[] CLASSPATH_RESOURCE_LOCATIONS =
	{ "classpath:/static/" };

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry)
	{
		registry.addResourceHandler("resources/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
	}
}