package com.stackroute.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Project Already Exists.")
public class ProjectAlreadyExistsException extends Exception{
    public ProjectAlreadyExistsException(String message){
        super(message);
    }
}
