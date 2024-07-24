package com.dd.bikes.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dd.bikes.model.User;
import com.dd.bikes.repository.IUserRepository;
import com.dd.bikes.service.IUserService;

@Service
public class UserService implements IUserService {

	BCryptPasswordEncoder brcEncoder = new BCryptPasswordEncoder();
	private static final Logger logger = LogManager.getLogger(UserService.class);
	private IUserRepository userRepository;

	public UserService(IUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User addUser(User user) {
		String encryptedPass = brcEncoder.encode(user.getPassword());
		user.setPassword(encryptedPass);
		User savedUser = this.userRepository.save(user);
		logger.info("User saved successfully with Id {} ", savedUser.getUserId());
		return savedUser;
	}

	@Override
	public boolean checkUsernameExist(String username) {
		User existingUser = userRepository.findByUsername(username);
		if (existingUser != null) {
			logger.debug("Username already exists {} ", username);
			return true;
		}
		return false;
	}

	@Override
	public String authenticate(String username, String password) {
	    User existingUser = userRepository.findByUsername(username);
	    if (existingUser == null) {
	        return "User not exist in database";
	    }

	    if (brcEncoder.matches(password, existingUser.getPassword())) {
	        return "User is valid Authenticated successful";
	    } else {
	        return "Authentication failed password does not match";
	    }
	}

	@Override
	public boolean checkEmailExist(String email) {
		User existingUser = userRepository.findByEmail(email);
		if (existingUser != null) {
			logger.debug("Email is present {}", email);
			return true;
		}
		return false;
	}
}
