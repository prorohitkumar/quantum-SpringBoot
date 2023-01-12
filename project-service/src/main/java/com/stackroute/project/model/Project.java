package com.stackroute.project.model;

import com.stackroute.project.enums.ProjectStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Document
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Project {

    @Id
    private String projectId;
    private String projectName;
    private String projectDescription;
    private String location;
    private long startDate;
    private int durationInWeeks;
    private boolean teamCreated;
    private String projectQR;
    private List<Expertise> expertise;
    private Manager manager;
    private ProjectStatus projectStatus;
    private List<UserProfile> allocatedResources;

}
