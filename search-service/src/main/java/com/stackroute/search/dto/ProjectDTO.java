package com.stackroute.search.dto;

import com.stackroute.search.enums.ProjectStatus;
import com.stackroute.search.model.Expertise;
import com.stackroute.search.model.Manager;
import com.stackroute.search.model.UserProfile;
import lombok.*;

import java.time.LocalDate;
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
