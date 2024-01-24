package com.usersantiago.app.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.usersantiago.app.persistence.entities.UserEntity;
import com.usersantiago.app.persistence.repositories.UserRepository;
import com.usersantiago.app.services.IAuthService;
import com.usersantiago.app.services.IJWTUtilityService;
import com.usersantiago.app.services.models.dtos.LoginDTO;
import com.usersantiago.app.services.models.dtos.ResponseDTO;
import com.usersantiago.app.services.models.validations.UserValidations;


@Service
public class AuthServiceImpl implements IAuthService {

	private UserRepository userRepository;
	private IJWTUtilityService jwtUtilityService;
	private UserValidations userValidations;

	public AuthServiceImpl(UserRepository userRepository, IJWTUtilityService jwtUtilityService,
			UserValidations userValidations) {
		super();
		this.userRepository = userRepository;
		this.jwtUtilityService = jwtUtilityService;
		this.userValidations = userValidations;
	}

	@Override
	public HashMap<String, String> login(LoginDTO login) throws Exception {
		try {
			HashMap<String, String> jwt = new HashMap<>();

			Optional<UserEntity> user = userRepository.findByEmail(login.getEmail());
			if (user.isEmpty()) {
				jwt.put("error", "user not registered!");
				return jwt;
			}

			// verify pass pasada con la que ya esta
			if (verifyPassword(login.getPassword(), user.get().getPassword())) {
				jwt.put("jwt", jwtUtilityService.generateJWT(user.get().getId()));
			} else {
				jwt.put("error", "Authentication failed!");
			}
			return jwt;

		} catch (IllegalArgumentException e) {
		     System.err.println("Error generating JWT: " + e.getMessage());
	         throw new Exception("Error generating JWT", e);
		}
		catch (Exception e) {
            System.err.println("Unknown error: " + e.toString());
            throw new Exception("Unknown error", e);
        }

	}

	@Override
	public ResponseDTO register(UserEntity user) throws Exception {
		try {

			ResponseDTO response = userValidations.validate(user);

			if (response.getNumOfErrors() > 0) {
				return response;
			}

			List<UserEntity> getAllUsers = userRepository.findAll();

			for (UserEntity repeatFields : getAllUsers) {
				if (repeatFields != null) {
					response.setNumOfErrors(1);
					response.setMessage("User already exists!");
					return response;
				}
			}

			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
			user.setPassword(encoder.encode(user.getPassword()));
			userRepository.save(user);
			response.setMessage("User created succesfully!");
			
			return response;
		} catch (Exception e) {
			throw new Exception(e.toString());
		}
	}

	private boolean verifyPassword(String enteredPassword, String storedPassword) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.matches(enteredPassword, storedPassword);
	}

}
