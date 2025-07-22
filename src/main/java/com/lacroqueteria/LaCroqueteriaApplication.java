package com.lacroqueteria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class LaCroqueteriaApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(LaCroqueteriaApplication.class, args);
	}
	
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(LaCroqueteriaApplication.class);
	}

}
