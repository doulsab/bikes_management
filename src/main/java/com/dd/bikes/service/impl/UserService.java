package com.dd.bikes.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.dd.bikes.model.LoginRequest;
import com.dd.bikes.model.User;
import com.dd.bikes.repository.IUserRepository;
import com.dd.bikes.service.IUserService;

@Service
public class UserService implements IUserService {

	private static final Logger logger = LogManager.getLogger(UserService.class);
	private IUserRepository userRepository;

	public UserService(IUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User addUser(User user) {
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
	public boolean authenticate(String username, String password) {
		User isUserPresent = this.userRepository.findByUsernameAndPassword(username, password);
		return isUserPresent != null;
	}
}
