package com.usersantiago.app.persistence.repositories;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.usersantiago.app.persistence.entities.CustomerEntity;
import com.usersantiago.app.services.IJWTUtilityService;

@Repository
public class CustomerRepository  {
	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final CustomerMapper mapper = new CustomerMapper();
	private final SimpleJdbcInsert jdbcInsert;
	private IJWTUtilityService jwtUtilityService;
	private final String table = "customer";

	public CustomerRepository(NamedParameterJdbcTemplate jdbcTemplate, DataSource dataSource,
			IJWTUtilityService jwtUtilityService) {
		this.jdbcTemplate = jdbcTemplate;
		this.jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(table).usingGeneratedKeyColumns("customer_id");
		this.jwtUtilityService = jwtUtilityService;
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

	

	
//	public List<CustomerEntity> getAllCustomers() {
//		String querySqlSelectAll = "SELECT customer_id, email, password FROM " + table;
//		return jdbcTemplate.query(querySqlSelectAll, mapper);
//	}
	
	public List<CustomerEntity> getAllCustomers() {
		String SELECT_QUERY_SQL = "SELECT customer_id, email, password FROM " + table;
	    return jdbcTemplate.query(SELECT_QUERY_SQL, new RowMapper<CustomerEntity>() {
	      public CustomerEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	  CustomerEntity customer = new CustomerEntity();
	    	  
	    	  customer.setIdCustomer(rs.getInt("customer_id"));
//	    	  customer.setTipoDocument(rs.getString("tipo_document"));
//	    	  customer.setDocument(rs.getString("document"));
//	    	  customer.setFirstName(rs.getString("first_name"));
//	    	  customer.setLastName(rs.getString("last_name"));
//	    	  customer.setBirthdate(rs.getDate("birthdate"));
//	    	  customer.setPhone(rs.getString("phone"));
	    	  customer.setEmail(rs.getString("email"));
	    	  customer.setPassword(rs.getString("password"));
	    	  return customer;
	      }
	    });
	  }


	private static class CustomerMapper implements RowMapper<CustomerEntity> {
		@Override
		public CustomerEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
			Integer customer_id = rs.getInt("customer_id");
			String email = rs.getString("email");
			String password = rs.getString("password");
			return new CustomerEntity(customer_id, email, password);
		}
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
