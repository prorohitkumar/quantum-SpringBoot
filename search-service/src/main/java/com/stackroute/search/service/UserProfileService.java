package com.stackroute.search.service;

import com.stackroute.search.exception.UserProfileAlreadyExistsException;
import com.stackroute.search.exception.UserProfileNotFoundException;
import com.stackroute.search.model.UserProfile;
import org.springframework.data.elasticsearch.core.SearchHits;

public interface UserProfileService {

    UserProfile addUser(UserProfile userProfile) throws UserProfileAlreadyExistsException;

    UserProfile updateUserDetails(String email,UserProfile userProfile) throws UserProfileNotFoundException;

    UserProfile getUserProfileById(String email) throws UserProfileNotFoundException;

    boolean deleteUserProfileById(String email);

    Iterable<UserProfile> getAllUsers();

    SearchHits<UserProfile> search(String keyword);

}
