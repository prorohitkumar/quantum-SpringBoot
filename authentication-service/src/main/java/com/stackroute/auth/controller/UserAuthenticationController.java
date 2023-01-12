package com.stackroute.auth.controller;

import com.stackroute.auth.model.User;
import com.stackroute.auth.exception.UserAlreadyExistsException;
import com.stackroute.auth.exception.UserIdAndPasswordMismatchException;
import com.stackroute.auth.exception.UserNotFoundException;
import com.stackroute.auth.security.SecurityTokenGenerator;
import com.stackroute.auth.service.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
public class UserAuthenticationController {


    private UserAuthenticationService userAuthenticationService;
    private SecurityTokenGenerator tokenGenerator;

    @Autowired
    public UserAuthenticationController(UserAuthenticationService userAuthenticationService, SecurityTokenGenerator tokenGenerator) {
        this.userAuthenticationService = userAuthenticationService;
        this.tokenGenerator = tokenGenerator;
    }


    @PostMapping("/register")
    public ResponseEntity<?> saveResource(@RequestBody User user) throws UserAlreadyExistsException {

        try {
            userAuthenticationService.saveUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (UserAlreadyExistsException e) {
            return new ResponseEntity<>("User Already Present", HttpStatus.CONFLICT);
        }

    }


    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody User user) throws UserNotFoundException, UserIdAndPasswordMismatchException {

        try {
            User getUserLoginDetails = userAuthenticationService.login(user);
            Map<String, String> jwtToken = null;
            if (getUserLoginDetails != null) {
                jwtToken = tokenGenerator.generateToken(user,userAuthenticationService.getUserRole(user.getEmail()));
            }
            return new ResponseEntity<>(jwtToken, HttpStatus.OK);
        } catch (UserIdAndPasswordMismatchException e) {
            return new ResponseEntity<>("Email or Password is wrong. Try Again", HttpStatus.CONFLICT);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("Email Not Present", HttpStatus.CONFLICT);
        }
    }


    @GetMapping("/action")
    public ResponseEntity<Object> getAction(@RequestParam String email) throws IOException, UserNotFoundException {

        try {
            String userRole = userAuthenticationService.getUserRole(email);
            Object newObject = userAuthenticationService.getAction(userRole);
            return new ResponseEntity<Object>(newObject, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Error occurred while reading file ", HttpStatus.CONFLICT);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>("Email Not Present", HttpStatus.CONFLICT);
        }
    }
}
