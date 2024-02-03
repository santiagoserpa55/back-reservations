package com.usersantiago.app.api;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.usersantiago.app.persistence.entities.CustomerEntity;
import com.usersantiago.app.services.models.dtos.CustomerDTO;

@Component
public class Mapper {
	
	public CustomerDTO toDTO(CustomerEntity customerEntity) {
		String tipoDocument = customerEntity.getTipoDocument();
		String document = customerEntity.getDocument();
		String firstName = customerEntity.getFirstName();
		String lastName = customerEntity.getLastName();
		String phone = customerEntity.getPhone();
		String email = customerEntity.getEmail();
		Date birthdate = customerEntity.getBirthdate();
		return new CustomerDTO(tipoDocument, document, firstName, lastName, phone, email, birthdate);
	}
	

}
