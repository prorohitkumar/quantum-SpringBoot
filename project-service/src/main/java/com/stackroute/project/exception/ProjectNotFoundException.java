package com.stackroute.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Project Not Found.")
public class ProjectNotFoundException extends Exception{
    public ProjectNotFoundException(String message){
        super(message);
    }
}
