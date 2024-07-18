package com.dd.bikes.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
		String username = loginRequest.getUsername();
		String password = loginRequest.getPassword();
		Map<String, String> response = new HashMap<>();

		String msg = userService.authenticate(username, password);
		if (msg.contains("User is valid")) {
			response.put("message", msg);
			return ResponseEntity.ok(response);
		} else {
			response.put("message", msg);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
		}
	}

}