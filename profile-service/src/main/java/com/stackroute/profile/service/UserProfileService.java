package com.stackroute.profile.service;

import com.stackroute.profile.model.UserProfile;
import com.stackroute.profile.exception.UserProfileAlreadyExistsException;
import com.stackroute.profile.exception.UserProfileNotFoundException;

import java.io.IOException;
import java.util.List;

public interface UserProfileService
{
    UserProfile addUser(UserProfile userProfile) throws UserProfileAlreadyExistsException;

    UserProfile updateUserDetails(String email,UserProfile userProfile) throws UserProfileNotFoundException;

    UserProfile getUserProfileById(String email) throws UserProfileNotFoundException;

    List<UserProfile> getAllUsers();

    Integer getProfileStatus(String email) throws UserProfileNotFoundException;

    UserProfile addPhoto(String email,String multipart) throws UserProfileNotFoundException, IOException;



}
