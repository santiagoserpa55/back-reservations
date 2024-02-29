package com.usersantiago.app.services;

import java.util.HashMap;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.usersantiago.app.exceptions.RequestValidationException;
import com.usersantiago.app.exceptions.ResourceNotFoundException;
import com.usersantiago.app.persistence.entities.CustomerEntity;
import com.usersantiago.app.persistence.repositories.CustomerRepository;
import com.usersantiago.app.services.models.dtos.CustomerCreationDTO;
import com.usersantiago.app.services.models.dtos.CustomerUpdateRequest;
import com.usersantiago.app.services.models.dtos.LoginDTO;

@Service
public class CustomerService {

	private final IcustomerDAO customerDAO;

	public CustomerService(IcustomerDAO customerDAO) {
		this.customerDAO = customerDAO;
	}

	public void saveCustomer(CustomerCreationDTO newCustomerRuquest) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

		CustomerCreationDTO newCustomer = new CustomerCreationDTO(
				newCustomerRuquest.tipoDocument(),
				newCustomerRuquest.document(),
				newCustomerRuquest.firstName(),
				newCustomerRuquest.lastName(),
				newCustomerRuquest.phone(),
				newCustomerRuquest.email(),
				encoder.encode(newCustomerRuquest.password()),
				newCustomerRuquest.birthdate());

		customerDAO.saveCustomer(newCustomer);

	}

	public HashMap<String, String> signin(LoginDTO customer) throws Exception {
		return customerDAO.signin(customer);
	}

	public List<CustomerEntity> getAllCustomers() {
		return customerDAO.getAllCustomers();
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
