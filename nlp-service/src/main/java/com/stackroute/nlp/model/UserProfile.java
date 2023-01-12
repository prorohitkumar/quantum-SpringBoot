package com.stackroute.nlp.model;

import com.stackroute.nlp.enums.Gender;
import com.stackroute.nlp.enums.UserRole;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserProfile {

    private String email;
    private String firstName;
    private String lastName;
    private String phoneNo;
    private Gender gender;
//    private LocalDate dateOfBirth;
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

}
