package com.stackroute.project.service;

import com.stackroute.project.enums.ProjectStatus;
import com.stackroute.project.exception.ProjectAlreadyExistsException;
import com.stackroute.project.exception.ProjectNotFoundException;
import com.stackroute.project.model.Manager;
import com.stackroute.project.model.Project;

import java.util.List;

public interface ProjectService {

    Project addProject(Project project) throws ProjectAlreadyExistsException;

    Project updateProject(String projectId, Project project) throws ProjectNotFoundException;

    Project getProjectById(String projectId) throws ProjectNotFoundException;

    List<Project> getAllProjects(String projectName, String managerEmail, String projectStatus);

    boolean isTesting();

}
