package com.usersantiago.app.services;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.usersantiago.app.persistence.entities.CustomerEntity;
import com.usersantiago.app.services.models.dtos.CustomerCreationDTO;
import com.usersantiago.app.services.models.dtos.LoginDTO;

public interface IcustomerDAO {
	Optional<CustomerEntity> selectCustomerByDocument(String document);
	void updateCustomer(CustomerEntity customer);
	List<CustomerEntity> getAllCustomers();
	Integer saveCustomer(CustomerCreationDTO newCustomer);
	boolean existsCustomerWithDocument(String document);
	boolean existsCustomerWithEmail(String email);
	HashMap<String, Object> signin(LoginDTO customer) throws Exception;
}
