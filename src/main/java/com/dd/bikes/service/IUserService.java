package com.dd.bikes.service;

import com.dd.bikes.model.User;

public interface IUserService {

	User addUser(User user);

	boolean checkUsernameExist(String username);

	boolean authenticate(String username, String password);

}
