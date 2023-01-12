package com.stackroute.auth.exception;

public class UserIdAndPasswordMismatchException extends Exception {

	public UserIdAndPasswordMismatchException(String message) {
        super(message);
    }

    public UserIdAndPasswordMismatchException() {

    }
}
