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

@RestController
@RequestMapping("api/v1/customers")
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerControllers {

  private final CustomerService customerService;
  private final Mapper mapper;

  // importante hacer la inyeccion de dependencias
  public CustomerControllers(CustomerService customerService, Mapper mapper) {
    this.customerService = customerService;
    this.mapper = mapper;
  }

  @GetMapping
  public Map<String, List<CustomerDTO>> getAllCustomers() {
    List<CustomerDTO> customerDTOs = customerService
        .getAllCustomers()
        .stream()
        .map(mapper::toDTO)
        .toList();
    return Map.of("data", customerDTOs);
  }

  @GetMapping ("{customerId}")
  public CustomerDTO getCustomer(
          @PathVariable("customerId") String customerId) {
    return customerService.getCustomer(customerId);
  }

  @PutMapping("{document}")
  public ResponseEntity<?> updateCustomer(
      @PathVariable("document") String document,
      @RequestBody CustomerUpdateRequest updateRequest) {
    customerService.updateCustomer(document, updateRequest);
    return ResponseEntity.status(204).body(new MessageResponse("Actualizacion exitosa."));
  }
}
