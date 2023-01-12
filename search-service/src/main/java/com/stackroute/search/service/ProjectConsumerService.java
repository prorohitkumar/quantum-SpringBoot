package com.stackroute.search.service;

import com.stackroute.search.aspect.LoggerAspect;
import com.stackroute.search.converter.Converter;
import com.stackroute.search.dto.ProjectDTO;
import com.stackroute.search.model.Project;
import com.stackroute.search.repository.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(LoggerAspect.class);

    private ProjectRepository projectRepository;

    Converter converter = new Converter();

    @Autowired
    public ProjectConsumerService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    // This method consumes the message from Rabbitmq queue
    @RabbitListener(queues = "${spring.rabbitmq.projectQueue}")
    public void recivedMessage(ProjectDTO projectDTO) {
        if (projectDTO.getAction().equals("create")) {
            addProjectFromQueue(projectDTO);
        }
        else if (projectDTO.getAction().equals("update")) {
            updateProjectFromQueue(projectDTO);
        }
    }

    //    This method is executed when there is a create action
    public void addProjectFromQueue(ProjectDTO projectDTO) {
        Project newProject = converter.projectDtoToProject(projectDTO);
        projectRepository.save(newProject);
        logger.info("Added Project "+projectDTO.getProjectName()+" successfully");
    }

    //    This method is executed when there is a update action
    public void updateProjectFromQueue(ProjectDTO projectDTO) {
        Project updateProject = converter.projectDtoToProject(projectDTO);
        projectRepository.save(updateProject);
        logger.info("Updated Project "+projectDTO.getProjectName()+" successfully");
    }
}
