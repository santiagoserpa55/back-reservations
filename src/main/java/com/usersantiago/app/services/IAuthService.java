package com.usersantiago.app.services;

import java.util.HashMap;

import com.usersantiago.app.persistence.entities.CustomerEntity;
import com.usersantiago.app.persistence.entities.UserEntity;
import com.usersantiago.app.services.models.dtos.LoginDTO;
import com.usersantiago.app.services.models.dtos.ResponseDTO;

public interface IAuthService {
	
	
	
	public HashMap<String, String> login(LoginDTO login) throws Exception;

	public ResponseDTO register(UserEntity user) throws Exception;

	

}
