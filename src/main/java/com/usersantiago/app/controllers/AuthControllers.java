package com.usersantiago.app.controllers;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usersantiago.app.persistence.entities.UserEntity;
import com.usersantiago.app.services.IAuthService;
import com.usersantiago.app.services.models.dtos.LoginDTO;
import com.usersantiago.app.services.models.dtos.ResponseDTO;

@RestController
@RequestMapping("/auth")
public class AuthControllers {

	private IAuthService authService;

	public AuthControllers(IAuthService authService) {
		super();
		this.authService = authService;
	}

	@PostMapping("/register")
	private ResponseEntity<ResponseDTO> register(@RequestBody UserEntity user) throws Exception {
		return new ResponseEntity<>(authService.register(user), HttpStatus.CREATED);		
	}

	@PostMapping("/login")
	private ResponseEntity<HashMap<String, String>> login(@RequestBody LoginDTO loginRequest) throws Exception {
		HashMap<String, String> login = authService.login(loginRequest);
		if (login.containsKey("jwt")) {
			return new ResponseEntity<>(login, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(login, HttpStatus.UNAUTHORIZED);			
		}
	}

}
