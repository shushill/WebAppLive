package com.sushil.project;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.Set;
import java.util.logging.Logger;

@SpringBootApplication
public class ProjectApplication extends SpringBootServletInitializer {
	private static final Logger logger = Logger.getLogger(ProjectApplication.class.getName());


	public static void main(String[] args) {

		SpringApplication.run(ProjectApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ProjectApplication.class);
	}
//	@Override
//	public void onStartup(Set<Class<?>> webAppInitializerClasses, ServletContext servletContext) throws ServletException {
//		logger.info("Initializing servlet context...");
//		super.onStartup(webAppInitializerClasses, servletContext);
//		// Additional custom initialization can be added here
//	}
}
