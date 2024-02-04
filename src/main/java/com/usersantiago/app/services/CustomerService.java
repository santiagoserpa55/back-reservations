package com.usersantiago.app.services;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.usersantiago.app.exceptions.DuplicateResourceException;
import com.usersantiago.app.persistence.entities.CustomerEntity;
import com.usersantiago.app.persistence.repositories.CustomerRepository;
import com.usersantiago.app.services.models.dtos.CustomerCreationDTO;
import com.usersantiago.app.services.models.dtos.LoginDTO;

@Service
public class CustomerService {

	private final CustomerRepository customerRepository;

	public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public void saveCustomer(CustomerCreationDTO newCustomerRuquest) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

		CustomerEntity newCustomer = new CustomerEntity(
				newCustomerRuquest.tipoDocument(),
				newCustomerRuquest.document(),
				newCustomerRuquest.firstName(),
				newCustomerRuquest.lastName(),
				newCustomerRuquest.phone(),
				newCustomerRuquest.email(),
				encoder.encode(newCustomerRuquest.password()),
				newCustomerRuquest.birthdate());

		customerRepository.saveCustomer(newCustomer);

	}

	public HashMap<String, String> signin(LoginDTO customer) throws Exception {
		return customerRepository.signin(customer);
	}

	public List<CustomerEntity> getAllCustomers() {
		return customerRepository.getAllCustomers();
	}

	public boolean existsByEmail(String email) {
		return customerRepository.existsCustomerWithEmail(email);
	}

	public boolean existsByDocument(String document) {
		return customerRepository.existsCustomerWithDocument(document);
	}

}
