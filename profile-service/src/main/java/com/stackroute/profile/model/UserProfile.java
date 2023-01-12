package com.stackroute.profile.model;

import com.stackroute.profile.enums.Gender;
import com.stackroute.profile.enums.UserRole;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Document
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserProfile implements Serializable
{
    @Id
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNo;
    private Gender gender;
    private LocalDate dateOfBirth;
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

//  @Size(max = 4000)
    private String image;
}
