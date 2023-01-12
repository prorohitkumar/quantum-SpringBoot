package com.stackroute.resourceallocate.exception;

public class ProjectAlreadyExistsException extends Exception
{
    public ProjectAlreadyExistsException() {
    }
    public ProjectAlreadyExistsException(String message) {
        super(message);
    }
}