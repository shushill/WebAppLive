package com.sushil.project;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.Set;
import java.util.logging.Logger;

@SpringBootApplication
public class ProjectApplication extends SpringBootServletInitializer {

//@SpringBootApplication
//public class ProjectApplication {

	public static void main(String[] args) {

		SpringApplication.run(ProjectApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ProjectApplication.class);
	}

}
