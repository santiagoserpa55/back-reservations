package com.usersantiago.app.services.models.dtos;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
		//customer.setBirthdate(rs.getBirthdate("birthdate").);
		customer.setBirthdate(rs.getDate("birthdate").toLocalDate());
		customer.setPhone(rs.getString("phone"));
		customer.setEmail(rs.getString("email"));
		customer.setPassword(rs.getString("password"));
		return customer;
	}
	
//	public CustomerEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
//	    BeanPropertyRowMapper<CustomerEntity> rowMapper = new BeanPropertyRowMapper<>(CustomerEntity.class);
//	    return rowMapper.mapRow(rs, rowNum);
//	}
	
	
//    @Override
//    public CustomerEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
//        return new CustomerEntity(
//                rs.getInt("customer_id"),
//                rs.getString("tipo_document"),
//                rs.getString("document"),
//                rs.getString("first_name"),
//                rs.getString("last_name"),
//                rs.getDate("birthdate").toLocalDate(),
//                rs.getString("phone"),
//                rs.getString("email"),
//                rs.getString("password"));
//    }
//	
	
}
