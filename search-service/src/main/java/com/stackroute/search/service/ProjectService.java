package com.stackroute.search.service;

import com.stackroute.search.exception.ProjectAlreadyExistsException;
import com.stackroute.search.exception.ProjectNotFoundException;
import com.stackroute.search.model.Project;

import java.util.List;
import java.util.Set;

public interface ProjectService {

    Project addProject(Project project) throws ProjectAlreadyExistsException;

    Project updateProjectDetails(String projectId, Project project) throws ProjectNotFoundException;

    Project getProjectById(String projectId) throws ProjectNotFoundException;

    Iterable<Project> getAllProjects();

    List<Project> search(String keyword);

}
