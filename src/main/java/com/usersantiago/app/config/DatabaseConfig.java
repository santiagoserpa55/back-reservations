package com.usersantiago.app.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class DatabaseConfig {

	// Bean que obtendra todos los datos de conex del file app.yml
	@Bean
	@ConfigurationProperties(prefix = "datasource.my-connection")
	public DataSource crudDataSource() {
		return DataSourceBuilder.create().build();
	}

	// este te permite trabajar con parametros mucho mas facil
	@Bean
	public NamedParameterJdbcTemplate appSecNamedParameterJdbcTemplate(JdbcTemplate appSecJdbcTemplate) {
		return new NamedParameterJdbcTemplate(appSecJdbcTemplate);
	}

}
