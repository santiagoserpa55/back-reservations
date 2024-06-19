package com.usersantiago.app.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	@GetMapping
	public Map<String, List<CustomerDTO>> getAllCustomers() {
		List<CustomerDTO> customerDTOs = customerService.getAllCustomers().stream().map(mapper::toDTO)
				.collect(toList());
		return Map.of("data", customerDTOs);
	}
	
	@PutMapping("{document}")
	public ResponseEntity<?> updateCustomer(
	    @PathVariable("document") String document,
	    @RequestBody CustomerUpdateRequest updateRequest) {
	    customerService.updateCustomer(document, updateRequest);
		return ResponseEntity.status(204).body(new MessageResponse("Actualizacion exitosa."));    
	}
}
