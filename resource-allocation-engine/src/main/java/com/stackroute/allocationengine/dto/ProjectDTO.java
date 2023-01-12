package com.stackroute.allocationengine.dto;

import com.stackroute.allocationengine.enums.ProjectStatus;
import com.stackroute.allocationengine.model.Expertise;
import com.stackroute.allocationengine.model.Manager;
import com.stackroute.allocationengine.model.UserProfile;
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
    private String startDate;
    private int durationInWeeks;
    private boolean teamCreated;
    private List<Expertise> expertise;
    private Manager manager;
    private ProjectStatus projectStatus;
    private List<UserProfile> allocatedResources;
    private String action;

}
