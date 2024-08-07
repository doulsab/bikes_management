package com.dd.bikes.service;

import com.dd.bikes.model.User;

public interface IUserService {

	User addUser(User user);

	User checkUserExist(String username);

	User checkEmailExist(String email);

}
