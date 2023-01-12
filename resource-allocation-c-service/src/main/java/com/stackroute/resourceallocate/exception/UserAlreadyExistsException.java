package com.stackroute.resourceallocate.exception;

public class UserAlreadyExistsException extends Exception
{
    public UserAlreadyExistsException() {
    }
    public UserAlreadyExistsException(String message) {
        super(message);
    }

}
