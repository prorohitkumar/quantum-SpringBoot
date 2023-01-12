package com.stackroute.search.controller;

import com.stackroute.search.exception.UserProfileAlreadyExistsException;
import com.stackroute.search.exception.UserProfileNotFoundException;
import com.stackroute.search.model.UserProfile;
import com.stackroute.search.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("api/v1")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @PostMapping("/user")
    public ResponseEntity<?> registerUser(@RequestBody UserProfile user) throws UserProfileAlreadyExistsException
    {
        try
        {
            UserProfile newUserAdded=userProfileService.addUser(user);

            return new ResponseEntity<>(newUserAdded, HttpStatus.CREATED);
        }
        catch (UserProfileAlreadyExistsException existsException)
        {
            return new ResponseEntity<String>("User already exists",HttpStatus.CONFLICT);
        }

    }

    @PutMapping("/user")
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

    @GetMapping("/user")
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

    @GetMapping("/users")
    public ResponseEntity<?> fetchAllRegisteredUsers()
    {
        return new ResponseEntity<>(userProfileService.getAllUsers(),HttpStatus.OK);
    }

    @DeleteMapping("/user")
    public ResponseEntity<?> deleteUserById(@RequestParam("email") String email)
    {
        return new ResponseEntity<>(userProfileService.deleteUserProfileById(email), HttpStatus.OK);
    }

    @GetMapping("/users/search")
    public ResponseEntity<?> searchUsers(@RequestParam("value") String keyword)
    {
        return new ResponseEntity<>(userProfileService.search(keyword), HttpStatus.OK);
    }

}
