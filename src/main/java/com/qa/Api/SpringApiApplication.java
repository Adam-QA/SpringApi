package com.qa.Api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableWebMvc
public class SpringApiApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(SpringApiApplication.class, args);
	}

}
