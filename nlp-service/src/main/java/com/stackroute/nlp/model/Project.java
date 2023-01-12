package com.stackroute.nlp.model;

import com.stackroute.nlp.enums.ProjectStatus;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Project {

    private String projectId;
    private String projectName;
    private String projectDescription;
    private String location;
    private long startDate;
    private int durationInWeeks;
    private boolean teamCreated;
    private List<Expertise> expertise;
    private Manager manager;
    private ProjectStatus projectStatus;
    private List<UserProfile> allocatedResources;

}
