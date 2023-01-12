package com.stackroute.auth.exception;

public class UserNotFoundException extends Exception {

	public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException() {

    }
}
