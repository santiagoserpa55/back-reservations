package com.usersantiago.app.services.impl;

import java.util.Date;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import com.usersantiago.app.persistence.entities.CustomerEntity;
import com.usersantiago.app.persistence.repositories.ICustomerService;

@Service
public class CustomerServiceImpl implements ICustomerService {

	private final NamedParameterJdbcTemplate jdbcTemplate;
	// private final CustomerMapper mapper = new CustomerMapper();
	private final SimpleJdbcInsert jdbcInsert;
	private final String table = "customer";
	private CustomerEntity customerEntity;

	public CustomerServiceImpl(NamedParameterJdbcTemplate jdbcTemplate, DataSource dataSource) {
		this.jdbcTemplate = jdbcTemplate;
		this.jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(table).usingGeneratedKeyColumns("customer_id");
	}

	@Override
	public Integer saveCustomer(CustomerEntity customer) {
		Date now = new Date();
		
		return jdbcInsert.executeAndReturnKey(new MapSqlParameterSource()
				.addValue("tipo_document", customer.getTipoDocument())
				.addValue("document", customer.getDocument())
				.addValue("first_name", customer.getFirstName())
				.addValue("last_name", customer.getLastName())
				.addValue("phone", customer.getPhone())
				.addValue("email", customer.getEmail())
				.addValue("password", customer.getPassword())
				.addValue("birthdate", customer.getBirthdate())
				.addValue("rol", 1)
				.addValue("active", 1)
				.addValue("created_at", new Date())
				.addValue("updated_at", new Date()))
				.intValue();
	}

}
