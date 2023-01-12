package com.stackroute.profile.controller;

import com.stackroute.profile.model.UserProfile;
import com.stackroute.profile.exception.UserProfileAlreadyExistsException;
import com.stackroute.profile.exception.UserProfileNotFoundException;
import com.stackroute.profile.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/test/userprofile")
public class UserProfileControllerForTesting
{
    private UserProfileService userProfileService;

    @Autowired
    public UserProfileControllerForTesting(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserProfile user) throws UserProfileAlreadyExistsException
    {
        try
        {
            UserProfile newUserAdded=userProfileService.addUser(user);

            return new ResponseEntity<>(newUserAdded,HttpStatus.CREATED);
        }
        catch (UserProfileAlreadyExistsException existsException)
        {
            return new ResponseEntity<String>("User already exists",HttpStatus.CONFLICT);
        }

    }
    
    @PutMapping("/update")
    public ResponseEntity<?> updateUserProfileDetails(@RequestParam("email") String email,@RequestBody UserProfile userProfile) throws UserProfileNotFoundException
    {
        try
        {
            UserProfile updatedUserProfile= userProfileService.updateUserDetails(email,userProfile);
            return new ResponseEntity<>(updatedUserProfile,HttpStatus.OK);
        }
        catch (UserProfileNotFoundException notFoundException)
        {
            return new ResponseEntity<String>("User profile not found",HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/fetchuser")
    public ResponseEntity<?> fetchRegisteredUser(@RequestParam("email") String email) throws UserProfileNotFoundException
    {
        try
        {
            return new ResponseEntity<>(userProfileService.getUserProfileById(email),HttpStatus.OK);
        }
        catch (UserProfileNotFoundException notFoundException)
        {
            return new ResponseEntity<String>("User profile not found",HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/fetchusers")
    public ResponseEntity<?> fetchAllRegisteredUsers()
    {
        return new ResponseEntity<>(userProfileService.getAllUsers(),HttpStatus.OK);
    }
}
