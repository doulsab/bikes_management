package com.dd.bikes.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dd.bikes.model.RequestMailForm;
import com.dd.bikes.model.User;
import com.dd.bikes.service.IUserService;
import com.dd.bikes.service.impl.EmailService;

@RestController
public class EMailController {

//	Use constructor injection
	@Autowired
	private IUserService userService;

	@Autowired
	private EmailService emailService;

	private String MSG = "massage";

	private static final Logger logger = LogManager.getLogger(EMailController.class);

	Random random = new Random();

	@PostMapping("/send_otp/{email}")
	public ResponseEntity<?> forgotPass(@PathVariable String email) {

		Map<String, String> response = new HashMap<>();
		User existing = userService.checkEmailExist(email);

		if (existing == null) {
			response.put(MSG, "Email address is not matched.");
			return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
		}

//		Generate 6 digit OTP
		int generatedOTP = 100000 + random.nextInt(900000);

		logger.info("Generated OPT {}", generatedOTP);

		String appUsername = existing.getUsername();

		boolean isOtpSent = this.emailService.sendMail(email, generatedOTP, appUsername);

		if (isOtpSent) {
			return new ResponseEntity<>(generatedOTP, HttpStatus.ACCEPTED);
		}

		response.put(MSG, "Sending OPT failed.");
		return ResponseEntity.status(HttpStatus.CONFLICT).body(response);

	}

	@PostMapping("/submitForm")
	public ResponseEntity<Void> handleFormSubmission(@RequestBody RequestMailForm formData) {
		boolean mailRequested = emailService.sendFormSubmissionEmail(formData);

		if (mailRequested) {
			// Return status 202 Accepted if the email was sent successfully
			return ResponseEntity.status(HttpStatus.ACCEPTED).build();
		} else {
			// Return status 409 Conflict if the email sending failed
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

}
