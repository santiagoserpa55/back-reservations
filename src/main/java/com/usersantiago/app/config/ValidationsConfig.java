package com.usersantiago.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.usersantiago.app.services.models.validations.UserValidations;

@Configuration
public class ValidationsConfig {

  @Bean
  public UserValidations userValidations() {
    return new UserValidations();
  }

}
