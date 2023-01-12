package com.stackroute.auth.service;

import com.stackroute.auth.model.User;
import com.stackroute.auth.exception.UserAlreadyExistsException;
import com.stackroute.auth.exception.UserIdAndPasswordMismatchException;
import com.stackroute.auth.exception.UserNotFoundException;
import java.io.IOException;


public interface UserAuthenticationService {


    public User login(User user) throws UserNotFoundException, UserIdAndPasswordMismatchException;

    boolean saveUser(User user) throws UserAlreadyExistsException;

    public Object getAction(String role) throws IOException;

    public String getUserRole(String email) throws UserNotFoundException;


}
