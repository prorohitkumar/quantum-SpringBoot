package com.stackroute.allocationengine.model;

import lombok.*;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@NodeEntity(label = "UserProfile")
public class UserProfile {

    @Id
    private String email;
//    private String firstName;
//    private String lastName;
    private String name;
//    private String phoneNo;
//    private Gender gender;
//    private LocalDate dateOfBirth;
//    private UserRole userRole;
//    private String location;
//    private boolean anEmployee;
    private boolean availableForProject;
//    private String designation;
//    private float ctc;
//    private float experienceInYrs;
//    private List<DomainExperience> domainExperiences;
//    private List<Skill> skills;
//    private List<String> completedProjects;
}
