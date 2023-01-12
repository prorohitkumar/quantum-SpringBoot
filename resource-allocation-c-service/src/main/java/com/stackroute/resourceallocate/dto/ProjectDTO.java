package com.stackroute.resourceallocate.dto;

import com.stackroute.resourceallocate.enums.ProjectStatus;
import com.stackroute.resourceallocate.model.Expertise;
import com.stackroute.resourceallocate.model.Manager;
import com.stackroute.resourceallocate.model.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO
{
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