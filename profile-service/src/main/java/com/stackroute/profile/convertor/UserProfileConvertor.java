package com.stackroute.profile.convertor;

import com.stackroute.profile.dto.UserDTO;
import com.stackroute.profile.model.UserProfile;
import com.stackroute.profile.enums.UserRole;
import org.springframework.stereotype.Component;

@Component
public class UserProfileConvertor
{
    public UserDTO entitytoDTO(UserProfile userProfile)
    {
        UserDTO dto=new UserDTO();
        dto.setEmail(userProfile.getEmail());
        dto.setUserRole(userProfile.getUserRole());

        return dto;
    }

    public UserProfile dtoToEntity(UserDTO dto)
    {
        UserProfile userProfile=new UserProfile();
        userProfile.setEmail(dto.getEmail());
        userProfile.setUserRole(dto.getUserRole());

        return userProfile;
    }
}
