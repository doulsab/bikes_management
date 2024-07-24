package com.dd.bikes.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dd.bikes.service.IUserService;

@RestController
public class ForgotController {

//	Use constructor injection
	@Autowired
	private IUserService userService;
	Random random = new Random(1000);

	@PostMapping("/send_otp/{email}")
	public ResponseEntity<?> forgotPass(@PathVariable String email) {

		Map<String, String> response = new HashMap<>();
		boolean exist = userService.checkEmailExist(email);

		if (!exist) {
			response.put("message", "Email address is not matched.");
			return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
		}

//		Generate 6 digit OTP

		int generatedOTP = random.nextInt(999999);
		System.out.println("Generated OPT " + generatedOTP);
		
//		need to sent the opt to mail address
		
		
		return new ResponseEntity<>(generatedOTP, HttpStatus.ACCEPTED);
	}
}
