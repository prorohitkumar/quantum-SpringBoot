package com.stackroute.resourceallocate.dto;

import com.stackroute.resourceallocate.enums.Gender;
import com.stackroute.resourceallocate.enums.UserRole;
import com.stackroute.resourceallocate.model.DomainExperience;
import com.stackroute.resourceallocate.model.Skill;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO
{
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNo;
    private Gender gender;
    private String dateOfBirth;
    private UserRole userRole;
    private String location;
    private boolean anEmployee;
    private boolean availableForProject;
    private boolean showNotification;
    private String designation;
    private float ctc;
    private float experienceInYrs;
    private List<DomainExperience> domainExperiences;
    private List<Skill> skills;
    private List<String> completedProjects;
    private String action;
}
