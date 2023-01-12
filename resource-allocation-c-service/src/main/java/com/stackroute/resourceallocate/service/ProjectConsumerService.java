package com.stackroute.resourceallocate.service;

import com.stackroute.resourceallocate.convertor.Converter;
import com.stackroute.resourceallocate.dto.ProjectDTO;
import com.stackroute.resourceallocate.model.Expertise;
import com.stackroute.resourceallocate.model.ProfessionalDomain;
import com.stackroute.resourceallocate.model.Project;
import com.stackroute.resourceallocate.model.Skill;
import com.stackroute.resourceallocate.repository.ProjectRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectConsumerService implements RabbitListenerConfigurer
{
    private ProjectRepository projectRepository;

    private Converter converter=new Converter();

    @Autowired
    public ProjectConsumerService(ProjectRepository projectRepository)
    {
        this.projectRepository = projectRepository;
    }

    @RabbitListener(queues = "${spring.rabbitmq.projectAllocateQueue}")
    public void receiveMessage(ProjectDTO projectDTO)
    {
        if(projectDTO.getAction().equals("create"))
        {
            addProjectFromQueue(projectDTO);
        }
        if(projectDTO.getAction().equals("update"))
        {
            updateProjectFromQueue(projectDTO);
        }
    }

    private void addProjectFromQueue(ProjectDTO projectDTO)
    {
        Project newProject=converter.projectDtoToProject(projectDTO);
        projectRepository.save(newProject);
        createRelationshipForProjectAndLocation(projectDTO);
        createRelationshipForProjectAndProfessionalDomain(projectDTO);
        createRelationshipForProjectAndSkills(projectDTO);
    }

    private void updateProjectFromQueue(ProjectDTO projectDTO)
    {
        Project updatedProject= converter.projectDtoToProject(projectDTO);
        projectRepository.save(updatedProject);
        removeRelationships(projectDTO);
        createRelationshipForProjectAndLocation(projectDTO);
        createRelationshipForProjectAndProfessionalDomain(projectDTO);
        createRelationshipForProjectAndSkills(projectDTO);
    }

    /*
    This method is used to remove the previous relationships between the nodes and
    create new updated relationships between the nodes
     */
    public void removeRelationships(ProjectDTO projectDTO)
    {
        projectRepository.removeRelationships(projectDTO.getProjectId());
    }

    /*
     This method will create the relationship between Project and City nodes having relationship
     LOCATED_IN
     */
    public void createRelationshipForProjectAndLocation(ProjectDTO projectDTO)
    {
        projectRepository.createRelationshipForProjectAndLocation(projectDTO.getProjectId(), projectDTO.getLocation() );
    }

    /*
     This method will create the relationship between Project and Professional Domain nodes having relationship
     DEALS_IN
     */
    public void createRelationshipForProjectAndProfessionalDomain(ProjectDTO projectDTO)
    {
        String projectId= projectDTO.getProjectId();
        Expertise ex;
        for(int i=0;i<projectDTO.getExpertise().size();i++)
        {
            ex=projectDTO.getExpertise().get(i);
            projectRepository.createRelationshipForProjectAndDomain(projectId,ex.getDomainExperience());
        }
    }

    /*
       This method will create relationship between Project and TechnicalSkills denoting relationship
       REQUIRES_SKILL with a property level which will define the level of skill required as BASIC, INTERMEDIATE,
       ADVANCE.
    */
    public void createRelationshipForProjectAndSkills(ProjectDTO projectDTO)
    {
        String projectId= projectDTO.getProjectId();
        Expertise ex;
        for(int i=0;i<projectDTO.getExpertise().size();i++)
        {
            ex=projectDTO.getExpertise().get(i);
            for(int j=0;j<ex.getSkills().size();j++)
            {
                Skill s=ex.getSkills().get(j);
                projectRepository.createRelationshipForProjectAndSkillsNode(projectId,s.getSkillName(),String.valueOf(s.getLevel()));
            }
        }
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {

    }
}
