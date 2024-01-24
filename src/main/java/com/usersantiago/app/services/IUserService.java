package com.usersantiago.app.services;

import java.util.List;

import com.usersantiago.app.persistence.entities.UserEntity;

public interface IUserService {
	
	public List<UserEntity> findAllUsers();

}
