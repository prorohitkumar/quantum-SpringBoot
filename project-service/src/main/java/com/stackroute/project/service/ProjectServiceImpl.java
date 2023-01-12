package com.stackroute.project.service;

import com.stackroute.project.exception.ProjectAlreadyExistsException;
import com.stackroute.project.exception.ProjectNotFoundException;
import com.stackroute.project.model.Project;
import com.stackroute.project.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectServiceImpl implements ProjectService{

    private ProjectRepository projectRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, MongoTemplate mongoTemplate) {
        this.projectRepository = projectRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Project addProject(Project project) throws ProjectAlreadyExistsException {
        if(project.getProjectId() == null){
            project.setProjectId(UUID.randomUUID().toString());
        }else{
            Optional<Project> existingProject = projectRepository.findById(project.getProjectId());
            if(existingProject.isPresent()){
                throw new ProjectAlreadyExistsException("Project Already Exists.");
            }
        }

        return projectRepository.save(project);
    }

    @Override
    public Project updateProject(String projectId, Project project) throws ProjectNotFoundException {
        Optional<Project> existingProject = projectRepository.findById(projectId);
        if(existingProject.isEmpty()){
            throw new ProjectNotFoundException("Project Not Found.");
        }
        return projectRepository.save(project);
    }

    @Override
    public Project getProjectById(String projectId) throws ProjectNotFoundException {
        Optional<Project> project = projectRepository.findById(projectId);
        if(project.isEmpty()){
            throw new ProjectNotFoundException("Project Not Found.");
        }
        return project.get();
    }

    @Override
    public List<Project> getAllProjects(String location, String managerEmail, String projectStatus) {

        Query query = new Query();

        if(location == null){
            query.addCriteria(Criteria.where("location").regex(".*"));
        }else {
            query.addCriteria(Criteria.where("location").regex(location, "i"));
        }
        if(managerEmail == null){
            query.addCriteria(Criteria.where("manager.email").regex(".*"));
        }else {
            query.addCriteria(Criteria.where("manager.email").regex(managerEmail, "i"));
        }
        if(projectStatus == null){
            query.addCriteria(Criteria.where("projectStatus").regex(".*"));;
        }else {
            List<String> projectStatuses = Arrays.asList(projectStatus.split("\\s*,\\s*"));
            query.addCriteria(Criteria.where("projectStatus").in(projectStatuses));
        }

        return mongoTemplate.find(query, Project.class);


    }

    @Override
    public boolean isTesting() {
        return false;
    }


}
