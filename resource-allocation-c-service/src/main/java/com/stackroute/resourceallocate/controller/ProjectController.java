package com.stackroute.resourceallocate.controller;

import com.stackroute.resourceallocate.dto.ProjectDTO;
import com.stackroute.resourceallocate.exception.ProjectAlreadyExistsException;
import com.stackroute.resourceallocate.model.Project;
import com.stackroute.resourceallocate.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/resource/project")
public class ProjectController
{
    @Autowired
    private ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> addProject(@RequestBody ProjectDTO project)
    {
        try
        {
            Project newProjectAdded=projectService.addProject(project);
            return new ResponseEntity<>(newProjectAdded, HttpStatus.CREATED);
        }
        catch ( ProjectAlreadyExistsException existsException)
        {
            return new ResponseEntity<String>("Project already exists", HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/fetchdetails")
    public ResponseEntity<?> getProject(@RequestParam("projectName") String projectName)
    {
        try
        {
            return new ResponseEntity<>(projectService.getProjectByName(projectName),HttpStatus.OK);
        }
        catch (Exception exception)
        {
            return new ResponseEntity<String>("Project does not exist",HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/projects")
    public ResponseEntity<?> getAllProjects()
    {
        try
        {
            return new ResponseEntity<>(projectService.getAllProjects(),HttpStatus.OK);
        }
        catch (Exception exception)
        {
            return new ResponseEntity<String>("No projects found",HttpStatus.CONFLICT);
        }
    }


}
