package com.usersantiago.app.services;

import java.util.HashMap;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.usersantiago.app.api.Mapper;
import com.usersantiago.app.exceptions.RequestNotModified;
import com.usersantiago.app.exceptions.ResourceNotFoundException;
import com.usersantiago.app.persistence.entities.CustomerEntity;
import com.usersantiago.app.services.models.dtos.CustomerCreationDTO;
import com.usersantiago.app.services.models.dtos.CustomerDTO;
import com.usersantiago.app.services.models.dtos.CustomerUpdateRequest;
import com.usersantiago.app.services.models.dtos.LoginDTO;

@Service
public class CustomerService {

  private final IcustomerDAO customerDAO;
  private final Mapper mapper;

  public CustomerService(IcustomerDAO customerDAO,
                         Mapper mapper
  ) {
    this.customerDAO = customerDAO;
    this.mapper = mapper;
  }

  public void saveCustomer(CustomerCreationDTO newCustomerRuquest) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    CustomerCreationDTO newCustomer = new CustomerCreationDTO(
            newCustomerRuquest.documentType(),
            newCustomerRuquest.documentNumber(),
            newCustomerRuquest.firstName(),
            newCustomerRuquest.lastName(),
            newCustomerRuquest.phone(),
            newCustomerRuquest.email(),
            encoder.encode(newCustomerRuquest.password_cust()),
            newCustomerRuquest.birthdate());
    customerDAO.saveCustomer(newCustomer);
  }

  public HashMap<String, Object> signin(LoginDTO customer) throws Exception {
    return customerDAO.signin(customer);
  }

  public List<CustomerEntity> getAllCustomers() {
    return customerDAO.getAllCustomers();
  }

  public CustomerDTO getCustomer(String customerId) {
    return customerDAO.selectCustomerByDocument(customerId)
            .map(mapper::toDTO)
            .orElseThrow(() -> new ResourceNotFoundException(
                    "customer with id [%s] not found".formatted(customerId)
            ));
  }

  public boolean existsByEmail(String email) {
    return customerDAO.existsCustomerWithEmail(email);
  }

  public boolean existsByDocument(String document) {
    return customerDAO.existsCustomerWithDocument(document);
  }

  public void updateCustomer(String customerId, CustomerUpdateRequest updateRequest) {

    CustomerEntity customer = customerDAO.selectCustomerByDocument(customerId)
            .orElseThrow(() -> new ResourceNotFoundException(
                    "Cliente con document [%s] no encontrado".formatted(customerId)
            ));

    boolean changes = false;

    if (updateRequest.document_type() != null && !updateRequest.document_type().equals(customer.getDocumentType())) {
      customer.setDocumentType(updateRequest.document_type());
      changes = true;
    }

    if (updateRequest.firstName() != null && !updateRequest.firstName().equals(customer.getFirstName())) {
      customer.setFirstName(updateRequest.firstName());
      changes = true;
    }

    if (updateRequest.lastName() != null && !updateRequest.lastName().equals(customer.getLastName())) {
      customer.setLastName(updateRequest.lastName());
      changes = true;
    }

    if (updateRequest.phone() != null && !updateRequest.phone().equals(customer.getPhone())) {
      customer.setPhone(updateRequest.phone());
      changes = true;
    }

    if (updateRequest.birthdate() != null && !updateRequest.birthdate().equals(customer.getBirthdate())) {
      customer.setBirthdate(updateRequest.birthdate());
      changes = true;
    }

    if (!changes) {
      throw new RequestNotModified("No se encontraron cambios.");
    }
    customerDAO.updateCustomer(customer);
  }

}
