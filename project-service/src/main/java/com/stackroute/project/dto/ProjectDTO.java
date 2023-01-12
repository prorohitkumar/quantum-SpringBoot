package com.stackroute.project.dto;

import com.stackroute.project.enums.ProjectStatus;
import com.stackroute.project.model.Expertise;
import com.stackroute.project.model.Manager;
import com.stackroute.project.model.UserProfile;
import lombok.*;


import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProjectDTO {

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
    private String action;

}
