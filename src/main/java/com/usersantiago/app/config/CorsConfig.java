package com.usersantiago.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("/**")
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
				.allowedHeaders("Origin", "Content-type", "Accept", "Authorization").allowCredentials(true)
				.maxAge(3600);
		
		registry.addMapping("/api/v1/customers/**")
				.allowedOrigins("*")
				.allowedMethods("OPTIONS","POST","PUT")
				.allowedHeaders("Origin", "Content-Type","Accept", "Authorization")
				.allowCredentials(false)
				.maxAge(3600);
	}
}
