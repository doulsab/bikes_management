package com.dd.bikes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dd.bikes.exception.UsernameAlreadyExists;
import com.dd.bikes.model.LoginRequest;
import com.dd.bikes.model.User;
import com.dd.bikes.service.IUserService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

	private IUserService userService;

	public UserController(IUserService userService) {
		this.userService = userService;
	}

	@PostMapping(value = "/adduserdetails", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addUser(@RequestBody User user) {
//		Check the user name already exist or not
		boolean existingUser = userService.checkUsernameExist(user.getUsername());
		if (existingUser) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
		}
		User savedUser = userService.addUser(user);
		return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
	}

	@PostMapping(value = "/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> authenticateUser(@RequestBody LoginRequest loginRequest) {
		String username = loginRequest.getUsername();
		String password = loginRequest.getPassword();
		boolean isAuthenticated = userService.authenticate(username, password);
		if (isAuthenticated) {
			return ResponseEntity.ok("Authentication successful");
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
		}
	}
}