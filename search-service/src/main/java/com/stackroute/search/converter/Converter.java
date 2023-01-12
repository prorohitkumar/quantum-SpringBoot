package com.stackroute.search.converter;

import com.stackroute.search.dto.ProjectDTO;
import com.stackroute.search.dto.UserDTO;
import com.stackroute.search.model.Project;
import com.stackroute.search.model.UserProfile;

import java.time.LocalDate;

public class Converter {
    public UserProfile userDtoToUserProfile(UserDTO userDTO) {
//        LocalDate date = LocalDate.parse(userDTO.getDateOfBirth());
        UserProfile userProfile = new UserProfile(userDTO.getEmail(), userDTO.getFirstName(), userDTO.getLastName(),
                userDTO.getPhoneNo(), userDTO.getGender(), userDTO.getUserRole(), userDTO.getLocation(),
                userDTO.isAnEmployee(), userDTO.isAvailableForProject(), userDTO.isShowNotification(), userDTO.getDesignation(), userDTO.getCtc(),
                userDTO.getExperienceInYrs(), userDTO.getDomainExperiences(), userDTO.getSkills(), userDTO.getCompletedProjects());
        return userProfile;
    }

    public Project projectDtoToProject(ProjectDTO projectDTO) {
        Project project = new Project(projectDTO.getProjectId(), projectDTO.getProjectName(), projectDTO.getProjectDescription(),
                projectDTO.getLocation(), projectDTO.getStartDate(), projectDTO.getDurationInWeeks(), projectDTO.isTeamCreated(),
                projectDTO.getExpertise(), projectDTO.getManager(), projectDTO.getProjectStatus(), projectDTO.getAllocatedResources());
        return project;
    }
}
