package com.stackroute.search.model;

import com.stackroute.search.enums.Gender;
import com.stackroute.search.enums.UserRole;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDate;
import java.util.List;

@Document(indexName = "userprofile", shards = 5)
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserProfile {

    @Id
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
