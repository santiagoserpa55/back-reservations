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

import com.usersantiago.app.MessageResponse;
import com.usersantiago.app.api.Mapper;
import com.usersantiago.app.persistence.entities.CustomerEntity;
import com.usersantiago.app.services.CustomerService;
import com.usersantiago.app.services.models.dtos.CustomerCreationDTO;
import com.usersantiago.app.services.models.dtos.CustomerDTO;
import com.usersantiago.app.services.models.dtos.LoginDTO;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerControllers {

	private CustomerService customerService;
	private Mapper mapper;

	public CustomerControllers(CustomerService customerService, Mapper mapper) {
		this.customerService = customerService;
		this.mapper = mapper;
	}

	@GetMapping("/get-all")
	public List<CustomerDTO> getAllCustomers() {
		return customerService.getAllCustomers().stream().map(mapper::toDTO).collect(toList());
	}

	@PostMapping("/create")
	private ResponseEntity<?> saveCustomer(@RequestBody CustomerCreationDTO requestNewCustomer) throws Exception {
		if (customerService.existsByEmail(requestNewCustomer.email())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));	
		}
		customerService.saveCustomer(requestNewCustomer);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/signin")
	private ResponseEntity<HashMap<String, String>> signin(@RequestBody LoginDTO customer) throws Exception {
		HashMap<String, String> login = customerService.signin(customer);
		if (login.containsKey("jwt")) {
			return new ResponseEntity<>(login, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(login, HttpStatus.UNAUTHORIZED);
		}
	}

}
