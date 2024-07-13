package com.dd.bikes.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.dd.bikes.exception.UsernameAlreadyExists;
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
		// Check if the user name already exists
		User existingUser = userRepository.findByUsername(user.getUsername());
		if (existingUser != null) {
			logger.debug("Username already exists {} ", user.getUsername());
			throw new UsernameAlreadyExists("Username already exists");
		}
		user = userRepository.save(user);
		logger.info("Saved user with user id {} ", user.getUserId());
		return user;
	}

}
