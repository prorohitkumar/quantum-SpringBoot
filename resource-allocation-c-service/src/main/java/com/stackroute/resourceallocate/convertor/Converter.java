package com.stackroute.resourceallocate.convertor;

import com.stackroute.resourceallocate.dto.ProjectDTO;
import com.stackroute.resourceallocate.dto.UserDTO;
import com.stackroute.resourceallocate.model.Project;
import com.stackroute.resourceallocate.model.UserProfile;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class Converter
{
    public UserProfile userDtoToUserProfile(UserDTO userDTO)
    {
        UserProfile userProfile=new UserProfile(userDTO.getEmail(), userDTO.getFirstName()+" "+userDTO.getLastName(), userDTO.isAvailableForProject());
        return userProfile;
    }

    public Project projectDtoToProject(ProjectDTO projectDTO)
    {
        Project project=new Project(projectDTO.getProjectId(), projectDTO.getProjectName(), projectDTO.getProjectDescription());

        return project;
    }
}
