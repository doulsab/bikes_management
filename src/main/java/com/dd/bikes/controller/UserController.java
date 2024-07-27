package com.dd.bikes.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dd.bikes.model.LoginRequest;
import com.dd.bikes.model.User;
import com.dd.bikes.service.IUserService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

	private IUserService userService;
	public static final String MESSAGE = "message";

	public UserController(IUserService userService) {
		this.userService = userService;
	}

	@PostMapping(value = "/adduserdetails", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addUser(@Valid @RequestBody User user) {
		// Check the user name already exist or not
		if (userService.checkUsernameExist(user.getUsername())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
		}

		// Check if the email already exists
		if (userService.checkEmailExist(user.getEmail()) != null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
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
			response.put(MESSAGE, msg);
			return ResponseEntity.ok(response);
		} else {
			response.put(MESSAGE, msg);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
		}
	}

	@PatchMapping(value = "/update_password")
	public ResponseEntity<?> patchPassword(@RequestParam String email, @RequestParam String password) {
		Map<String, String> response = new HashMap<>();
		// Validate input
		if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
			response.put(MESSAGE, "Email and password must not be empty.");
			return ResponseEntity.badRequest().body(response);
		}
		User user = userService.checkEmailExist(email);
		if (user == null) {
			response.put(MESSAGE, "Email not exist");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		// Update the password
		user.setPassword(password);
		this.userService.addUser(user);

		// Return success response
		response.put(MESSAGE, "Password updated successfully.");
		return ResponseEntity.ok(response);
	}

}