package com.stackroute.resourceallocate.controller;


import com.stackroute.resourceallocate.dto.UserDTO;
import com.stackroute.resourceallocate.exception.UserAlreadyExistsException;
import com.stackroute.resourceallocate.model.UserProfile;
import com.stackroute.resourceallocate.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/v1/resource/user")
public class UserProfileController
{
    private UserProfileService userProfileService;

    @Autowired
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }


    @PostMapping("/register")
    public ResponseEntity<?> addUserNode(@RequestBody UserDTO user)
    {
        try
        {
            UserProfile newUserAdded= userProfileService.addUserDetails(user);
            return new ResponseEntity<>(newUserAdded, HttpStatus.CREATED);
        }
        catch ( UserAlreadyExistsException existsException)
        {
            return new ResponseEntity<String>("User already exists", HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/fetchuser")
    public ResponseEntity<?> getUser(@RequestParam("email") String email)
    {
        try
        {
            return new ResponseEntity<>(userProfileService.getUserByEmail(email),HttpStatus.OK);
        }
        catch (NoSuchElementException exception)
        {
            return new ResponseEntity<>("User not found",HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUserDetails()
    {
        try
        {
            //userProfileService.addRelationshipBetweenUserAndCity("abhishek1.gupta@gmail.com", "Jaipur");
            return new ResponseEntity<>(userProfileService.getAllUserDetails(),HttpStatus.OK);
        }
        catch (Exception exception)
        {
            return new ResponseEntity<String>("No user found",HttpStatus.CONFLICT);
        }
    }


}
