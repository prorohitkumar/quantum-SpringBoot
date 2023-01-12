package com.stackroute.search.controller;

import com.stackroute.search.exception.ProjectAlreadyExistsException;
import com.stackroute.search.exception.ProjectNotFoundException;
import com.stackroute.search.model.Project;
import com.stackroute.search.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("api/v1")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/project")
    public ResponseEntity<?> addProject(@RequestBody Project project) {
        try {
            final Project newAddedProject = projectService.addProject(project);
            return new ResponseEntity<>(newAddedProject, HttpStatus.CREATED);
        } catch (ProjectAlreadyExistsException alreadyExistsException) {
            return new ResponseEntity<String>("Project already exists", HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/project")
    public ResponseEntity<?> updateProject(@RequestParam("projectId") String projectId, @RequestBody Project project) {
        try {
            final Project updatedProject = projectService.updateProjectDetails(projectId, project);
            return new ResponseEntity<>(updatedProject, HttpStatus.OK);
        } catch (ProjectNotFoundException notFoundException) {
            return new ResponseEntity<String>("User profile not found",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/project")
    public ResponseEntity<?> fetchProject(@RequestParam("projectId") String projectId) {
        try {
            return new ResponseEntity<>(projectService.getProjectById(projectId), HttpStatus.OK);
        } catch (ProjectNotFoundException notFoundException) {
            return new ResponseEntity<String>("User profile not found",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/projects")
    public ResponseEntity<?> fetchAllProjects() {
        return new ResponseEntity<>(projectService.getAllProjects(), HttpStatus.OK);
    }

    @GetMapping("/projects/search")
    public ResponseEntity<?> searchProjects(@RequestParam("value") String keyword) {
        return new ResponseEntity<>(projectService.search(keyword), HttpStatus.OK);
    }

}
