package com.usersantiago.app.controllers;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usersantiago.app.persistence.entities.CustomerEntity;
import com.usersantiago.app.services.CustomerService;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerControllers {

	private CustomerService customerService;

	public CustomerControllers(CustomerService customerService) {
		super();
		this.customerService = customerService;
	}

	@GetMapping("/get-all")
	public List<CustomerEntity> getAllCustomers() {
		return customerService.getAllCustomers();
	}
	
	@PostMapping("/create")
	private ResponseEntity<Integer> saveCustomer(@RequestBody CustomerEntity customer) throws Exception {
		return new ResponseEntity<>(customerService.saveCustomer(customer), HttpStatus.CREATED);
	}
	
	@PostMapping("/signin")
	private ResponseEntity<HashMap<String, String>> signin(@RequestBody CustomerEntity customer) throws Exception {
		HashMap<String, String> login = customerService.signin(customer);
		if (login.containsKey("jwt")) {
			return new ResponseEntity<>(login, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(login, HttpStatus.UNAUTHORIZED);
		}
	}
	
	

}
