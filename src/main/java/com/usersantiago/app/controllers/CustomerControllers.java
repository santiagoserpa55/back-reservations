package com.usersantiago.app.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.usersantiago.app.api.Mapper;
import com.usersantiago.app.response.MessageResponse;
import com.usersantiago.app.services.CustomerService;
import com.usersantiago.app.services.models.dtos.CustomerDTO;
import com.usersantiago.app.services.models.dtos.CustomerUpdateRequest;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerControllers {

	private CustomerService customerService;
	private Mapper mapper;

	private CustomerControllers(CustomerService customerService, Mapper mapper) {
		this.customerService = customerService;
		this.mapper = mapper;
	}
	
	@GetMapping
	private Map<String, List<CustomerDTO>> getAllCustomers() {
		List<CustomerDTO> customerDTOs = customerService.getAllCustomers().stream().map(mapper::toDTO)
				.collect(toList());
		Map<String, List<CustomerDTO>> result = Map.of("data", customerDTOs);

		return result;
	}
	
	@PutMapping("{document}")
	private ResponseEntity<?> updateCustomer(
	    @PathVariable("document") String document,
	    @RequestBody CustomerUpdateRequest updateRequest) {
	    customerService.updateCustomer(document, updateRequest);
		return ResponseEntity.status(204).body(new MessageResponse("Actualizacion exitosa."));    
	}
}
