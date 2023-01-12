package com.stackroute.allocationengine.model;

import lombok.*;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@NodeEntity(label = "Project")
public class Project {

    @Id
    private String projectId;
    private String projectName;
    private String projectDescription;
//    private String location;
//    private LocalDate startDate;
//    private int durationInWeeks;
//    private boolean teamCreated;
//    private List<Expertise> expertise;
//    private Manager manager;
//    private ProjectStatus projectStatus;
//    private List<UserProfile> allocatedResources;
}
