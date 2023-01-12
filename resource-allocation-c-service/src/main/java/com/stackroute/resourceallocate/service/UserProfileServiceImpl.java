package com.stackroute.resourceallocate.service;


import com.stackroute.resourceallocate.dto.UserDTO;
import com.stackroute.resourceallocate.exception.UserAlreadyExistsException;
import com.stackroute.resourceallocate.model.DomainExperience;
import com.stackroute.resourceallocate.model.Skill;
import com.stackroute.resourceallocate.model.UserProfile;
import com.stackroute.resourceallocate.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserProfileServiceImpl implements UserProfileService
{
    private UserProfileRepository repository;

    @Autowired
    public UserProfileServiceImpl(UserProfileRepository repository) {
        this.repository = repository;
    }


    @Override
    public UserProfile addUserDetails(UserDTO user) throws UserAlreadyExistsException
    {
        String name= user.getFirstName().concat(" "+user.getLastName());
        UserProfile userProfile=new UserProfile(user.getEmail(), name,user.isAvailableForProject());
        Optional<UserProfile> existingUser=repository.findById(userProfile.getEmail());
        if(existingUser.isPresent())
        {
            throw new UserAlreadyExistsException("User already exists");
        }

        UserProfile newUserProfile=repository.save(userProfile);
        return newUserProfile;
    }

    /*Creating relationship between the nodes present in neo4j database -----------------------------------------*/
    @Override
    public void createRelationForUser(UserDTO userDTO)
    {
        repository.createRelationBetweenUserProfileAndOtherNodes(userDTO.getEmail(),userDTO.getCtc(), userDTO.getLocation(), userDTO.getDesignation() );
    }

    @Override
    public void createRelationBetweenUserAndTechnicalSkills(UserDTO userDTO)
    {
     String email= userDTO.getEmail();
     for(Skill skill: userDTO.getSkills())
     {
         repository.createRelationUserAndTechnicalSkill(email,skill.getSkillName(),String.valueOf(skill.getLevel()));
     }
    }

    @Override
    public void createRelationBetweenUserAndProfessionalDomain(UserDTO userDTO)
    {
        for(DomainExperience domain: userDTO.getDomainExperiences())
        {
            repository.createRelationUserAndProfessionalDomain(userDTO.getEmail(), domain.getDomainName(), domain.getDomainExperienceInYrs());
        }

    }

    @Override
    public void createRelationBetweenUserAndProfessionalExperience(UserDTO userDTO)
    {
        float experience= userDTO.getExperienceInYrs();

        if(experience>=1 && experience<3)
        {
            repository.createRelationshipUserAndProfessionalExperience(userDTO.getEmail(), "Fresher",experience);
        }
        if (experience>=3 && experience<7)
        {
            repository.createRelationshipUserAndProfessionalExperience(userDTO.getEmail(), "Intermediate",experience);
        }
        if(experience>=7 && experience<12)
        {
            repository.createRelationshipUserAndProfessionalExperience(userDTO.getEmail(), "Mid-senior",experience);
        }
        if(experience>=12 && experience<20)
        {
            repository.createRelationshipUserAndProfessionalExperience(userDTO.getEmail(), "Senior",experience);
        }

    }

    /*-----------------------------------------------------------------------------*/

    @Override
    public UserProfile getUserByEmail(String email)
    {
        Optional<UserProfile> existingUser=repository.findById(email);
        if(existingUser.isEmpty())
        {
            throw new NoSuchElementException();
        }
        return existingUser.get();
    }

    @Override
    public List<UserProfile> getAllUserDetails()
    {
        return (List<UserProfile>) repository.findAll();
    }

}
