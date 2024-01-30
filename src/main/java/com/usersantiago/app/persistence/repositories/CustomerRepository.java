package com.usersantiago.app.persistence.repositories;

import com.usersantiago.app.persistence.entities.CustomerEntity;

public interface ICustomerService {
	public Integer saveCustomer(CustomerEntity customer);

}
