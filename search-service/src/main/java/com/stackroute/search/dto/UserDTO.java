package com.stackroute.search.dto;

import com.stackroute.search.enums.Gender;
import com.stackroute.search.enums.UserRole;
import com.stackroute.search.model.DomainExperience;
import com.stackroute.search.model.Skill;
import lombok.*;

import java.time.LocalDate;
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
