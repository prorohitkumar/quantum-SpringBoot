package com.stackroute.allocationengine.dto;

import com.stackroute.allocationengine.enums.Gender;
import com.stackroute.allocationengine.enums.UserRole;
import com.stackroute.allocationengine.model.DomainExperience;
import com.stackroute.allocationengine.model.Skill;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {
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
