package com.stackroute.profile.service;


import com.stackroute.profile.model.UserProfile;
import com.stackroute.profile.exception.UserProfileAlreadyExistsException;
import com.stackroute.profile.exception.UserProfileNotFoundException;
import com.stackroute.profile.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class UserProfileServiceImpl implements UserProfileService
{

    private UserProfileRepository userProfileRepository;

    @Autowired
    public UserProfileServiceImpl(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public UserProfile addUser(UserProfile userProfile) throws UserProfileAlreadyExistsException
    {

        Optional<UserProfile> existingUser=userProfileRepository.findById(userProfile.getEmail());
        if(existingUser.isPresent())
        {
            throw new UserProfileAlreadyExistsException("User already exists");
        }

        UserProfile newUserProfile=userProfileRepository.insert(userProfile);

        if(newUserProfile==null)
        {
            return null;
        }
        return newUserProfile;
    }

    @Override
    public UserProfile updateUserDetails(String email,UserProfile userProfile) throws UserProfileNotFoundException
    {

        Optional<UserProfile> existingUser=userProfileRepository.findById(email);

        if(existingUser.isPresent())
        {
            return userProfileRepository.save(userProfile);
        }

        throw new UserProfileNotFoundException();
    }

    @Override
    public UserProfile getUserProfileById(String email) throws UserProfileNotFoundException
    {
        Optional<UserProfile> existingUser=userProfileRepository.findById(email);
        if(existingUser==null)
        {
            throw new UserProfileNotFoundException();
        }
        return existingUser.get();
    }

    @Override
    public List<UserProfile> getAllUsers()
    {
        return userProfileRepository.findAll();
    }

    @Override
    public Integer getProfileStatus(String email) throws UserProfileNotFoundException {
        Optional<UserProfile> existingUser=userProfileRepository.findById(email);
        if(existingUser==null)
        {
            throw new UserProfileNotFoundException();
        }
        final UserProfile userProfile = existingUser.get();
        int statusToReturn = 0;
        if(userProfile.getEmail() != null)
            statusToReturn = statusToReturn + 1;
        if(userProfile.getFirstName() != null)
            statusToReturn = statusToReturn + 1;
        if(userProfile.getLastName() != null)
            statusToReturn = statusToReturn + 1;
        if(userProfile.getPhoneNo() != null)
            statusToReturn = statusToReturn + 1;
        if(userProfile.getGender() != null)
            statusToReturn = statusToReturn + 1;
        if(userProfile.getDateOfBirth() != null)
            statusToReturn = statusToReturn + 1;
        if(userProfile.getLocation() != null)
            statusToReturn = statusToReturn + 1;
        if(userProfile.getDesignation() != null)
            statusToReturn = statusToReturn + 1;
        if(userProfile.getCtc() != 0.0f)
            statusToReturn = statusToReturn + 1;
        if(userProfile.getExperienceInYrs() != 0.0f)
            statusToReturn = statusToReturn + 1;
        if(!(userProfile.getDomainExperiences() == null))
            statusToReturn = statusToReturn + 1;
        if(!(userProfile.getSkills() == null))
            statusToReturn = statusToReturn + 1;
        statusToReturn = (statusToReturn * 100) / 12;
        return statusToReturn;
    }

    @Override
    public UserProfile addPhoto(String email,String multipart) throws UserProfileNotFoundException, IOException {
        
        Optional<UserProfile> existingUser=userProfileRepository.findById(email);

        if(existingUser.isPresent())
        {
            UserProfile userProfile=existingUser.get();
            userProfile.setImage(multipart);
            return userProfileRepository.save(userProfile);
        }

        throw new UserProfileNotFoundException();
    }
}
