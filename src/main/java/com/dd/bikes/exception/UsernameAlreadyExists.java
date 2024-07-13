package com.dd.bikes.exception;

public class UsernameAlreadyExists extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UsernameAlreadyExists(String message) {
		super(message);
	}

}
