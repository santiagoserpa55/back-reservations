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
		customer.setDocumentType(rs.getString("document_type"));
		customer.setDocumentNumber(rs.getString("document_number"));
		customer.setFirstName(rs.getString("first_name"));
		customer.setLastName(rs.getString("last_name"));
		customer.setBirthdate(rs.getDate("birthdate").toLocalDate());
		customer.setPhone(rs.getString("phone"));
		customer.setEmail(rs.getString("email"));
		customer.setPassword(rs.getString("password_cust"));
		return customer;
	}
}
