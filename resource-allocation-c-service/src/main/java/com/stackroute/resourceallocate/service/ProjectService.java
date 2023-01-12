package com.stackroute.resourceallocate.service;


import com.stackroute.resourceallocate.dto.ProjectDTO;
import com.stackroute.resourceallocate.exception.ProjectAlreadyExistsException;
import com.stackroute.resourceallocate.model.Project;

import java.util.List;

public interface ProjectService
{
    Project addProject(ProjectDTO project) throws ProjectAlreadyExistsException;
    Project getProjectByName(String projectName);
    List<Project> getAllProjects();
}