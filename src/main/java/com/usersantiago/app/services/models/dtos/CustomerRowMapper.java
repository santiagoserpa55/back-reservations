package com.usersantiago.app.services.models.dtos;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.usersantiago.app.persistence.entities.CustomerEntity;

@Component
public class CustomerRowMapper implements RowMapper<CustomerEntity> {

	@Override
	public CustomerEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
		CustomerEntity customer = new CustomerEntity();
		customer.setIdCustomer(rs.getInt("customer_id"));
		customer.setTipoDocument(rs.getString("tipo_document"));
		customer.setDocument(rs.getString("document"));
		customer.setFirstName(rs.getString("first_name"));
		customer.setLastName(rs.getString("last_name"));
		customer.setBirthdate(rs.getDate("birthdate"));
		customer.setPhone(rs.getString("phone"));
		customer.setEmail(rs.getString("email"));
		customer.setPassword(rs.getString("password"));
		return customer;

	}

}
