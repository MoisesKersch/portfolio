package com.portfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@SpringBootApplication
@Controller
public class PortfolioApplication extends WebMvcConfigurationSupport
{
	public static void main(String[] args)
	{
		SpringApplication.run(PortfolioApplication.class, args);
	}
}
