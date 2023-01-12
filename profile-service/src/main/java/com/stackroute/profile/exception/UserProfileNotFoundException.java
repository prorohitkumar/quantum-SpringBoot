package com.stackroute.profile.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UserProfileNotFoundException extends Exception
{
    public UserProfileNotFoundException(String message) {
        super(message);
    }

    public UserProfileNotFoundException() {
    }
}
