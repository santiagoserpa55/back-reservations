package com.usersantiago.app.services;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.usersantiago.app.persistence.entities.CustomerEntity;
import com.usersantiago.app.persistence.repositories.CustomerRepository;

@Service
public class CustomerService {

	private final CustomerRepository customerRepository;

	public CustomerService(CustomerRepository customerRepository) {
		super();
		this.customerRepository = customerRepository;
	}

	public Integer saveCustomer(CustomerEntity newCustomer) {
		return customerRepository.saveCustomer(newCustomer);
	}

	public HashMap<String, String> signin(CustomerEntity customer) throws Exception {
		return customerRepository.signin(customer);
	}

	public List<CustomerEntity> getAllCustomers() {
		return customerRepository.getAllCustomers();
	}

}
