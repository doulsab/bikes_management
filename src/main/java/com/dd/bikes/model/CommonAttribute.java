package com.dd.bikes.model;

import org.springframework.stereotype.Component;

@Component
public class CommonAttribute {

	enum authRoles {
		ADMIN, USER
	}

	// Allow access to static resources
	public final String[] publicUrls = { "/css/**", "/js/**", "/images/**", "/app/**", "/vendor/**", "/", "/index",
			"/login", "/authenticate", "/forgotpass", "/send_otp/*", "/verifyopt", "/changePassword", "/update_password",
			"/addUser", "/editBikeDetails", "/add", "/dashboard","/adduserdetails" };
}
