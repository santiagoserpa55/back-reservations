package com.usersantiago.app.api;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.usersantiago.app.persistence.entities.CustomerEntity;
import com.usersantiago.app.services.models.dtos.CustomerDTO;

@Component
public class Mapper {
	
	public CustomerDTO toDTO(CustomerEntity customerEntity) {
		int customerId = customerEntity.getIdCustomer();
		String tipoDocument = customerEntity.getTipoDocument();
		String document = customerEntity.getDocument();
		String firstName = customerEntity.getFirstName();
		String lastName = customerEntity.getLastName();
		String phone = customerEntity.getPhone();
		String email = customerEntity.getEmail();
		LocalDate birthdate = customerEntity.getBirthdate();
		return new CustomerDTO(customerId, tipoDocument, document, firstName, lastName, phone, email, birthdate);
	}
}
