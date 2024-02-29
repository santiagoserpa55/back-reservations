package com.usersantiago.app.persistence.repositories;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.usersantiago.app.persistence.entities.CustomerEntity;
import com.usersantiago.app.services.IJWTUtilityService;
import com.usersantiago.app.services.IcustomerDAO;
import com.usersantiago.app.services.models.dtos.CustomerRowMapper;
import com.usersantiago.app.services.models.dtos.LoginDTO;

@Repository
public class CustomerRepository implements IcustomerDAO {
	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final CustomerRowMapper rowMapper;
	private final JdbcTemplate jdbcTemplatequerys;
	private final SimpleJdbcInsert jdbcInsert;
	private IJWTUtilityService jwtUtilityService;
	private final String table = "customer";

	public CustomerRepository(NamedParameterJdbcTemplate jdbcTemplate, DataSource dataSource,
			JdbcTemplate jdbcTemplateQuerys, IJWTUtilityService jwtUtilityService, CustomerRowMapper rowMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(table).usingGeneratedKeyColumns("customer_id");
		this.jwtUtilityService = jwtUtilityService;
		this.rowMapper = rowMapper;
		this.jdbcTemplatequerys = jdbcTemplateQuerys;
	}

	public Integer saveCustomer(CustomerEntity customer) {
		Date now = new Date();
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
		var querySqlSelectAll = "SELECT customer_id, tipo_document, document,first_name, last_name, "
				+ "phone, email, password, birthdate FROM " + table + " WHERE active = 1";
		return jdbcTemplate.query(querySqlSelectAll, rowMapper);
	}

	public boolean existsCustomerWithEmail(String email) {
		String sql = "SELECT COUNT(customer_id) FROM customer WHERE email = :email";
		Map<String, Object> paramMap = Collections.singletonMap("email", email);
		Integer count = jdbcTemplate.queryForObject(sql, paramMap, Integer.class);
		return count != null && count > 0;
	}

	public Optional<CustomerEntity> selectUserByEmail(String email) {
		String sql = "SELECT customer_id, tipo_document, document,first_name, last_name,"
				+ "phone, email, password, birthdate FROM customer WHERE email = :email";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("email", email);

		return jdbcTemplate.query(sql, paramMap, rowMapper).stream().findFirst();
	}

	public HashMap<String, String> signin(LoginDTO customer) throws Exception {
		try {
			HashMap<String, String> jwt = new HashMap<>();
			Optional<CustomerEntity> usersByEmail = selectUserByEmail(customer.email());

			if (usersByEmail.isEmpty()) {
				jwt.put("error", "user not registered!");
				return jwt;
			}

			if (verifyPassword(customer.password(), usersByEmail.get().getPassword())) {
				String data = usersByEmail.get().getTipoDocument()
						.concat(usersByEmail.get().getDocument().concat(usersByEmail.get().getIdCustomer().toString()));

				jwt.put("data", data);

				jwt.put("jwt", jwtUtilityService.generateJWT(usersByEmail.get().getIdCustomer()));
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

	public boolean existsCustomerWithDocument(String document) {
		String sql = "SELECT COUNT(customer_id) FROM customer WHERE document = :document";
		Map<String, Object> paramMap = Collections.singletonMap("document", document);
		Integer count = jdbcTemplate.queryForObject(sql, paramMap, Integer.class);
		return count != null && count > 0;
	}

	@Override
	public void updateCustomer(CustomerEntity customerUpdate) {

		if (customerUpdate.getTipoDocument() != null) {
			String sql = "UPDATE public.customer SET tipo_document=? WHERE document=?";
			int result = jdbcTemplatequerys.update(sql, customerUpdate.getTipoDocument(), customerUpdate.getDocument());
			System.out.println("Actualizado " + result);
		}
		
		if (customerUpdate.getFirstName() != null) {
			String sql = "UPDATE public.customer SET first_name = ? WHERE document = ?";
			int result = jdbcTemplatequerys.update(sql, customerUpdate.getFirstName(), customerUpdate.getDocument());
			System.out.println("Actualizdo " + result);
		}
		
		if (customerUpdate.getLastName() != null) {
			String sql = "UPDATE public.customer SET last_name = ? WHERE document = ?";
			int result = jdbcTemplatequerys.update(sql, customerUpdate.getLastName(), customerUpdate.getDocument());
			System.out.println("Actualizdo " + result);
		}
		
		if (customerUpdate.getPhone() != null) {
			String sql = "UPDATE public.customer SET phone = ? WHERE document = ?";
			int result = jdbcTemplatequerys.update(sql, customerUpdate.getPhone(), customerUpdate.getDocument());
			System.out.println("Actualizdo " + result);
		}
		
		if (customerUpdate.getBirthdate() != null) {
			String sql = "UPDATE public.customer SET birthdate = ? WHERE document = ?";
			int result = jdbcTemplatequerys.update(sql, customerUpdate.getBirthdate(), customerUpdate.getDocument());
			System.out.println("Actualizdo " + result);
		}
	}

	@Override
	public Optional<CustomerEntity> selectCustomerByDocument(String document) {
		var sql = """
				SELECT customer_id, tipo_document, document, first_name, last_name, phone, email,
				password, birthdate
				FROM public.customer WHERE document = ?;
								""";
		return jdbcTemplatequerys.query(sql, rowMapper, document).stream().findFirst();
	}

}
