package com.usersantiago.app.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usersantiago.app.persistence.entities.UserEntity;
import com.usersantiago.app.services.IUserService;

@RestController
@RequestMapping("/user")

public class UserControllers {
	private IUserService userService;

	public UserControllers(IUserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/find-all")
	private ResponseEntity<List<UserEntity>> getAllUsers() {
		return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);		
	}
}
