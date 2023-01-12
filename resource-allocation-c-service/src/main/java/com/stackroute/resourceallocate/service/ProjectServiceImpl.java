package com.stackroute.resourceallocate.service;

import com.stackroute.resourceallocate.dto.ProjectDTO;
import com.stackroute.resourceallocate.exception.ProjectAlreadyExistsException;
import com.stackroute.resourceallocate.model.Project;
import com.stackroute.resourceallocate.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService
{
    private ProjectRepository projectRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Project addProject(ProjectDTO projectDTO) throws ProjectAlreadyExistsException
    {
        Project project=new Project(projectDTO.getProjectId(), projectDTO.getProjectName(), projectDTO.getProjectDescription());

        Optional<Project> existingProject=projectRepository.findById(project.getProjectId());
        if(existingProject.isPresent())
        {
            throw new ProjectAlreadyExistsException("Project already exists");
        }

        Project newProject=projectRepository.save(project);

        return newProject;
    }

    @Override
    public Project getProjectByName(String projectName)
    {
        Project existingProject=projectRepository.findByName(projectName);
        if(existingProject==null)
        {
            throw new NoSuchElementException("Project does not exist");
        }
        return existingProject;
    }

    @Override
    public List<Project> getAllProjects()
    {
        return (List<Project>) projectRepository.findAll();
    }
}
