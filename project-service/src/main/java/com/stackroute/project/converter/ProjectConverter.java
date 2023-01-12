package com.stackroute.project.converter;

import com.stackroute.project.dto.ProjectDTO;
import com.stackroute.project.model.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectConverter {

    public ProjectDTO entityToDTO(String action, Project project){
        ProjectDTO projectDTO = new ProjectDTO();

        projectDTO.setProjectId(project.getProjectId());
        projectDTO.setProjectName(project.getProjectName());
        projectDTO.setProjectDescription(project.getProjectDescription());
        projectDTO.setLocation(project.getLocation());
        projectDTO.setStartDate(project.getStartDate());
        projectDTO.setDurationInWeeks(project.getDurationInWeeks());
        projectDTO.setTeamCreated(project.isTeamCreated());
        projectDTO.setExpertise(project.getExpertise());
        projectDTO.setManager(project.getManager());
        projectDTO.setProjectStatus(project.getProjectStatus());
        projectDTO.setAllocatedResources(project.getAllocatedResources());
        projectDTO.setAction(action);

        return projectDTO;
    }

}
