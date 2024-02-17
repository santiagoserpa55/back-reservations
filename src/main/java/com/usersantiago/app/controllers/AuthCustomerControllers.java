package com.usersantiago.app.controllers;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.usersantiago.app.api.Mapper;
import com.usersantiago.app.response.MessageResponse;
import com.usersantiago.app.services.CustomerService;
import com.usersantiago.app.services.models.dtos.CustomerCreationDTO;
import com.usersantiago.app.services.models.dtos.LoginDTO;

@RestController
@ResponseStatus
@ResponseBody
@RequestMapping("/api/v1/auth")
public class AuthCustomerControllers {
	
	private CustomerService customerService;
	private Mapper mapper;

	private AuthCustomerControllers(CustomerService customerService, Mapper mapper) {
		this.customerService = customerService;
		this.mapper = mapper;
	}
	
	@PostMapping("/signup")
	private ResponseEntity<?> saveCustomer(@RequestBody CustomerCreationDTO requestNewCustomer) throws Exception {
		if (customerService.existsByDocument(requestNewCustomer.document())) {
			return ResponseEntity.status(409).body(new MessageResponse("Error: Document is already in use!"));
		}

		if (customerService.existsByEmail(requestNewCustomer.email())) {
			return ResponseEntity.status(409).body(new MessageResponse("Error: Email is already in use!"));
		}

		customerService.saveCustomer(requestNewCustomer);
		return ResponseEntity.status(201).body((new MessageResponse("User registered successfully!")));
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
