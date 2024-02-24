package com.usersantiago.app.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.usersantiago.app.api.Mapper;

import com.usersantiago.app.services.CustomerService;
import com.usersantiago.app.services.models.dtos.CustomerDTO;

import static java.util.stream.Collectors.toList;

@RestController

@RequestMapping("/api/v1/customers")
public class CustomerControllers {

	private CustomerService customerService;
	private Mapper mapper;

	private CustomerControllers(CustomerService customerService, Mapper mapper) {
		this.customerService = customerService;
		this.mapper = mapper;
	}
	
	@GetMapping("/all")
	private Map<String, List<CustomerDTO>> getAllCustomers() {
		List<CustomerDTO> customerDTOs = customerService.getAllCustomers().stream().map(mapper::toDTO)
				.collect(toList());

		// Crear un mapa con la clave "customers" y la lista de DTO de clientes como valor
		Map<String, List<CustomerDTO>> result = Map.of("data", customerDTOs);

		return result;
	}
}
