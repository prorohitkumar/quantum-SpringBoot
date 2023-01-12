package com.stackroute.resourceallocate.service;

import com.stackroute.resourceallocate.convertor.Converter;
import com.stackroute.resourceallocate.dto.UserDTO;
import com.stackroute.resourceallocate.model.DomainExperience;
import com.stackroute.resourceallocate.model.Skill;
import com.stackroute.resourceallocate.model.UserProfile;
import com.stackroute.resourceallocate.repository.UserProfileRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileConsumerService implements RabbitListenerConfigurer
{
    private UserProfileRepository userProfileRepository;

    Converter converter = new Converter();

    @Autowired
    public UserProfileConsumerService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @RabbitListener(queues = "${spring.rabbitmq.userResourceAllocationQueue}")
    public void receiveMessage(UserDTO userDTO)
    {
        String action=userDTO.getAction();
        if(action.equals("create"))
        {
            addUserFromQueue(userDTO);
        }
        if(action.equals("update"))
        {
            updateUserFromQueue(userDTO);
        }

    }

    public void addUserFromQueue(UserDTO userDTO)
    {
        UserProfile newUserProfile=converter.userDtoToUserProfile(userDTO);
        userProfileRepository.save(newUserProfile);
    }

    public void updateUserFromQueue(UserDTO userDTO)
    {
        UserProfile updatedUserProfile=converter.userDtoToUserProfile(userDTO);
        userProfileRepository.save(updatedUserProfile);
        removeRelationship(userDTO);
        createRelationForUser(userDTO);
        createRelationBetweenUserAndProfessionalExperience(userDTO);
        if(userDTO.getSkills() != null)
            createRelationBetweenUserAndTechnicalSkills(userDTO);
        if(userDTO.getDomainExperiences() != null)
            createRelationBetweenUserAndProfessionalDomain(userDTO);

    }

    /*
    This method is used to remove the previous relationships between the nodes and
    create new updated relationships between the nodes
    */
    public void removeRelationship(UserDTO userDTO)
    {
        userProfileRepository.removeRelationships(userDTO.getEmail());
    }

    public void createRelationForUser(UserDTO userDTO)
    {
        userProfileRepository.createRelationBetweenUserProfileAndOtherNodes(userDTO.getEmail(),userDTO.getCtc(), userDTO.getLocation(), userDTO.getDesignation() );
    }

    public void createRelationBetweenUserAndProfessionalExperience(UserDTO userDTO)
    {
        float experience= userDTO.getExperienceInYrs();

        if(experience>=1 && experience<3)
        {
            userProfileRepository.createRelationshipUserAndProfessionalExperience(userDTO.getEmail(), "Fresher",experience);
        }
        if (experience>=3 && experience<7)
        {
            userProfileRepository.createRelationshipUserAndProfessionalExperience(userDTO.getEmail(), "Intermediate",experience);
        }
        if(experience>=7 && experience<12)
        {
            userProfileRepository.createRelationshipUserAndProfessionalExperience(userDTO.getEmail(), "Mid-senior",experience);
        }
        if(experience>=12 && experience<20)
        {
            userProfileRepository.createRelationshipUserAndProfessionalExperience(userDTO.getEmail(), "Senior",experience);
        }

    }

    public void createRelationBetweenUserAndTechnicalSkills(UserDTO userDTO)
    {
        String email= userDTO.getEmail();
        for(Skill skill: userDTO.getSkills())
        {
            userProfileRepository.createRelationUserAndTechnicalSkill(email,skill.getSkillName(),String.valueOf(skill.getLevel()));
        }
    }

    public void createRelationBetweenUserAndProfessionalDomain(UserDTO userDTO)
    {
        for(DomainExperience domain: userDTO.getDomainExperiences())
        {
            userProfileRepository.createRelationUserAndProfessionalDomain(userDTO.getEmail(), domain.getDomainName(), domain.getDomainExperienceInYrs());
        }

    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar)
    { }

}
