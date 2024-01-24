package com.usersantiago.app.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.usersantiago.app.persistence.entities.UserEntity;
import com.usersantiago.app.persistence.repositories.UserRepository;
import com.usersantiago.app.services.IUserService;

@Service
public class UserServiceImpl implements IUserService {
	
	private UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public List<UserEntity> findAllUsers() {
		return userRepository.findAll();
	}
	

}
