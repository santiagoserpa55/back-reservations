package com.usersantiago.app.services;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.usersantiago.app.exceptions.DuplicateResourceException;
import com.usersantiago.app.exceptions.RequestValidationException;
import com.usersantiago.app.exceptions.ResourceNotFoundException;
import com.usersantiago.app.persistence.entities.CustomerEntity;
import com.usersantiago.app.persistence.repositories.CustomerRepository;
import com.usersantiago.app.services.models.dtos.CustomerCreationDTO;
import com.usersantiago.app.services.models.dtos.CustomerUpdateRequest;
import com.usersantiago.app.services.models.dtos.LoginDTO;

@Service
public class CustomerService {

	private final CustomerRepository customerRepository;
	private final IcustomerDAO customerDAO;

	public CustomerService(CustomerRepository customerRepository,
			IcustomerDAO customerDAO) {
		this.customerRepository = customerRepository;
		this.customerDAO = customerDAO;
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

	public void updateCustomer(String customerId, CustomerUpdateRequest updateRequest) {

		CustomerEntity customer = customerDAO.selectCustomerByDocument(customerId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Cliente con document [%s] no encontrado".formatted(customerId)
						));
		
		boolean changes = false;
		
		if (updateRequest.tipoDocument() != null && !updateRequest.tipoDocument().equals(customer.getTipoDocument())) {
			customer.setTipoDocument(updateRequest.tipoDocument());
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
			throw new RequestValidationException("No se encontraron cambios.");
		}
		
		customerDAO.updateCustomer(customer);
		
	}

}
