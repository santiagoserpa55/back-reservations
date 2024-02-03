package com.usersantiago.app.persistence.repositories;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;


import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.usersantiago.app.persistence.entities.CustomerEntity;
import com.usersantiago.app.services.IJWTUtilityService;
import com.usersantiago.app.services.models.dtos.CustomerRowMapper;

@Repository
public class CustomerRepository {
	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final CustomerRowMapper rowMapper;
	private final SimpleJdbcInsert jdbcInsert;
	private IJWTUtilityService jwtUtilityService;
	private final String table = "customer";

	public CustomerRepository(NamedParameterJdbcTemplate jdbcTemplate, DataSource dataSource,
			IJWTUtilityService jwtUtilityService, CustomerRowMapper rowMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(table).usingGeneratedKeyColumns("customer_id");
		this.jwtUtilityService = jwtUtilityService;
		this.rowMapper = rowMapper;
	}

	public Integer saveCustomer(CustomerEntity customer) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
		Date now = new Date();
		customer.setPassword(encoder.encode(customer.getPassword()));

		return jdbcInsert
				.executeAndReturnKey(new MapSqlParameterSource().addValue("tipo_document", customer.getTipoDocument())
						.addValue("document", customer.getDocument()).addValue("first_name", customer.getFirstName())
						.addValue("last_name", customer.getLastName()).addValue("phone", customer.getPhone())
						.addValue("email", customer.getEmail()).addValue("password", customer.getPassword())
						.addValue("birthdate", customer.getBirthdate()).addValue("rol", 1).addValue("active", 1)
						.addValue("created_at", new Date()).addValue("updated_at", new Date()))
				.intValue();
	}

	public List<CustomerEntity> getAllCustomers() {
		var querySqlSelectAll = "SELECT * FROM " + table;
		return jdbcTemplate.query(querySqlSelectAll, rowMapper);
	}

	public HashMap<String, String> signin(CustomerEntity customer) throws Exception {
		try {
			HashMap<String, String> jwt = new HashMap<>();

			List<CustomerEntity> usersByEmail = getAllCustomers();

			if (usersByEmail.isEmpty()) {
				jwt.put("error", "user not registered!");
				return jwt;
			}

			CustomerEntity user = usersByEmail.get(0); // Obtén el primer usuario de la lista

			if (verifyPassword(customer.getPassword(), user.getPassword())) {

				jwt.put("jwt", jwtUtilityService.generateJWT(user.getIdCustomer()));
			} else {
				jwt.put("error", "Authentication failed!");
			}
			return jwt;

		} catch (IllegalArgumentException e) {
			System.err.println("Error generating JWT: " + e.getMessage());
			throw new Exception("Error generating JWT", e);
		} catch (Exception e) {
			System.err.println("Unknown error: " + e.toString());
			throw new Exception("Unknown error", e);
		}
	}

	private boolean verifyPassword(String enteredPassword, String storedPassword) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();// bean que creamos en SecurityConfig
		return encoder.matches(enteredPassword, storedPassword);
	}

}
