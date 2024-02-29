package com.usersantiago.app.services;

import java.util.Optional;

import com.usersantiago.app.persistence.entities.CustomerEntity;

public interface IcustomerDAO {
	Optional<CustomerEntity> selectCustomerByDocument(String document);
	void updateCustomer(CustomerEntity customer);
}
