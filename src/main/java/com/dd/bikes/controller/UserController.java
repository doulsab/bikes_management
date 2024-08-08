package com.dd.bikes.controller;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dd.bikes.jwt.JWTTokenConfig;
import com.dd.bikes.model.LoginRequest;
import com.dd.bikes.model.User;
import com.dd.bikes.service.IUserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class UserController {
	BCryptPasswordEncoder brcEncoder = new BCryptPasswordEncoder();
	private final IUserService userService;
	private final JWTTokenConfig jwtConfig;

	public static final String MESSAGE = "message";

	@PostMapping(value = "/adduserdetails", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addUser(@Valid @RequestBody User user) {
		// Check the user name already exist or not
		if (userService.checkUserExist(user.getUsername()) != null) {
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
		Map<String, Object> response = new HashMap<>();
		String token = "";
		User existingUser = userService.checkUserExist(username);

		if (existingUser != null) {
			if (!brcEncoder.matches(password, existingUser.getPassword())) {
				response.put(MESSAGE, "Authentication failed password does not match");
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}

			String role = existingUser.getRole();
			Collection<? extends GrantedAuthority> authorities;

			if (role == null || role.trim().isEmpty()) {
				authorities = Collections.emptyList();
			} else {
				authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));
			}

			// Generate token with authorities
			token = this.jwtConfig.createToken(username, existingUser.getEmail(), authorities);

			List<String> roles = authorities.stream().map(GrantedAuthority::getAuthority).toList();

			response.put(MESSAGE, "User is valid. Authentication successful.");
			response.put("token", token);
			response.put("roles", roles);

			return ResponseEntity.ok(response);
		} else {
			response.put(MESSAGE, "User not exist in database");
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