package com.stackroute.search.service;

import com.stackroute.search.aspect.LoggerAspect;
import com.stackroute.search.exception.ProjectAlreadyExistsException;
import com.stackroute.search.exception.ProjectNotFoundException;
import com.stackroute.search.model.Project;
import com.stackroute.search.repository.ProjectRepository;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProjectServiceImpl implements ProjectService{

    @Autowired
    private ProjectRepository projectRepository;

    private static final Logger logger = LoggerFactory.getLogger(LoggerAspect.class);

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public Project addProject(Project project) throws ProjectAlreadyExistsException {
        Optional<Project> existingProject = projectRepository.findById(project.getProjectId());
        if(existingProject.isPresent())
        {
            throw new ProjectAlreadyExistsException("Project already exists");
        }

        Project newProject=projectRepository.save(project);

        if(newProject==null)
        {
            return null;
        }
        return newProject;
    }

    @Override
    public Project updateProjectDetails(String projectId, Project project) throws ProjectNotFoundException {
        Optional<Project> existingProject = projectRepository.findById(projectId);

        if(existingProject.isPresent())
        {
            return projectRepository.save(project);
        }

        throw new ProjectNotFoundException();
    }

    @Override
    public Project getProjectById(String projectId) throws ProjectNotFoundException {
        Optional<Project> existingProject = projectRepository.findById(projectId);

        if(existingProject.isEmpty())
        {
            throw new ProjectNotFoundException();
        }

        return existingProject.get();
    }

    @Override
    public Iterable<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public List<Project> search(String keyword) {
        QueryStringQueryBuilder queryBuilder = QueryBuilders.queryStringQuery("*"+keyword+"*")
                .field("projectId")
                .field("projectName")
                .field("projectDescription")
                .field("location")
                .field("expertise.designation")
                .field("expertise.domainExperience")
                .field("expertise.skills.skillName")
                .field("expertise.skills.level")
                .field("manager.email")
                .field("manager.firstName")
                .field("manager.lastName")
                .field("projectStatus")
                .field("allocatedResources.email")
                .field("allocatedResources.firstName")
                .field("allocatedResources.lastName")
                .field("allocatedResources.phoneNo")
                .field("allocatedResources.gender")
                .field("allocatedResources.userRole")
                .field("allocatedResources.location")
                .field("allocatedResources.designation")
                .field("allocatedResources.domainExperiences.domainName")
                .field("allocatedResources.skills.skillName")
                .field("allocatedResources.skills.level")
                .field("allocatedResources.completedProjects");
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withFilter(queryBuilder).build();
        List<SearchHit<Project>> searchHitList = elasticsearchRestTemplate.search(searchQuery, Project.class).getSearchHits();
        List<Project> projects = new ArrayList<>();
        for (SearchHit<Project> projectSearchHit: searchHitList) {
            projects.add(projectSearchHit.getContent());
        }
        return projects;
    }

}
