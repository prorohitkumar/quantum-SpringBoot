package com.stackroute.resourceallocate.service;



import com.stackroute.resourceallocate.dto.UserDTO;
import com.stackroute.resourceallocate.exception.UserAlreadyExistsException;
import com.stackroute.resourceallocate.model.UserProfile;

import java.util.List;

public interface UserProfileService
{
    UserProfile addUserDetails(UserDTO user) throws UserAlreadyExistsException;
    UserProfile getUserByEmail(String email);
    List<UserProfile> getAllUserDetails();
    public void createRelationForUser(UserDTO userDTO);
    public void createRelationBetweenUserAndTechnicalSkills(UserDTO userDTO);
    public void createRelationBetweenUserAndProfessionalDomain(UserDTO userDTO);
    public void createRelationBetweenUserAndProfessionalExperience(UserDTO userDTO);

}
