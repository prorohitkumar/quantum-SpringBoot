package com.stackroute.allocationengine.service;

import com.stackroute.allocationengine.aspect.LoggerAspect;
import com.stackroute.allocationengine.dto.ProjectDTO;
import com.stackroute.allocationengine.dto.ProjectRecommendationResponse;
import com.stackroute.allocationengine.dto.User;
import com.stackroute.allocationengine.enums.SkillLevel;
import com.stackroute.allocationengine.exception.ResourcesNotSufficientException;
import com.stackroute.allocationengine.model.Expertise;
import com.stackroute.allocationengine.model.Skill;
import com.stackroute.allocationengine.model.UserProfile;
import com.stackroute.allocationengine.repository.QueryRepository;
import com.stackroute.allocationengine.repository.UserProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private static final Logger logger = LoggerFactory.getLogger(LoggerAspect.class);

    private UserProfileRepository userProfileRepository;
    private QueryRepository queryRepository;

    @Autowired
    public UserProfileServiceImpl(UserProfileRepository userProfileRepository, QueryRepository queryRepository) {
        this.userProfileRepository = userProfileRepository;
        this.queryRepository = queryRepository;
    }

    @Override
    public List<ProjectRecommendationResponse> getRecommendedUsers(final ProjectDTO projectDTO) throws ResourcesNotSufficientException {

        List<ProjectRecommendationResponse> toReturn = new ArrayList<>();
        final String location = projectDTO.getLocation();

//        Iterating through out the list of Expertise needed for project
        for (Expertise expertise: projectDTO.getExpertise()) {

            ProjectRecommendationResponse response = new ProjectRecommendationResponse();
            response.setDesignation(expertise.getDesignation());

            logger.info(expertise.getDomainExperience());

//            Returns the list of users that are avaliable for projects, filters the users w.r.t location, experience, designation, and domain
            List<UserProfile> userProfiles = userProfileRepository.getRelatedUsers(location, expertise.getMinExperienceNeeded(), expertise.getMaxExperienceNeeded(), expertise.getDesignation(), expertise.getDomainExperience());
            logger.info("Before Skill Filtering: "+expertise.getDesignation()+" - "+userProfiles.size());

            // Returns the list of users whose skills matches
            List<UserProfile> filteredUserProfiles = matchSkills(userProfiles, expertise.getSkills());
            logger.info("After Skill Filtering: "+expertise.getDesignation()+" - "+filteredUserProfiles.size());

            if (filteredUserProfiles.size() >= (expertise.getNoOfResources() * 3)) {
                // randomly picking the required number of resources
                response.setResourcesSuggested(
                        convertUserProfileListToUserList(limitNoOfResources(filteredUserProfiles, expertise.getNoOfResources() * 3), expertise.getDesignation())
                );
            }
            else {
                throw new ResourcesNotSufficientException("Not Enough Resources");
            }

            response.setNoOfResources(response.getResourcesSuggested().size());
            logger.info("Sending: "+response.getNoOfResources()+" resources");
            toReturn.add(response);
        }
        return toReturn;
    }

//    retrives the skills of resource and compares with the requirement
    private List<UserProfile> matchSkills(final List<UserProfile> userProfiles, final List<Skill> skills) {

        List<UserProfile> userProfileList = new ArrayList<>();

        for (UserProfile userProfile: userProfiles) {

            List<Boolean> skillCheckList = new ArrayList<>();

            for (Skill requiredSkill: skills) {
                if (requiredSkill.getLevel().toString().equals("BASIC")) {
                    if ( queryRepository.getUserWithRequiredSkillAndLevel(
                            userProfile.getEmail(), requiredSkill.getSkillName(), Arrays.asList("BASIC", "INTERMEDIATE", "ADVANCE")) != null )
                        skillCheckList.add(true);
                    else
                        skillCheckList.add(false);
                }
                else if (requiredSkill.getLevel().toString().equals("INTERMEDIATE")) {
                    if ( queryRepository.getUserWithRequiredSkillAndLevel(
                            userProfile.getEmail(), requiredSkill.getSkillName(), Arrays.asList("INTERMEDIATE", "ADVANCE")) != null )
                        skillCheckList.add(true);
                    else
                        skillCheckList.add(false);
                }
                else if (requiredSkill.getLevel().toString().equals("ADVANCE")) {
                    if ( queryRepository.getUserWithRequiredSkillAndLevel(
                            userProfile.getEmail(), requiredSkill.getSkillName(), Arrays.asList("ADVANCE")) != null )
                        skillCheckList.add(true);
                    else
                        skillCheckList.add(false);
                }
            }

            if (! skillCheckList.contains(false))
                userProfileList.add(userProfile);
        }

        return userProfileList;
    }

//    mapping the skills from neo4j database to Skill model
    private List<Skill> convertMapListToSkillList(final List<Map<String, SkillLevel>> skillsList) {
        List<Skill> skills = new ArrayList<>();
        for (Map<String, SkillLevel> skill: skillsList) {
            skills.add(new Skill(skill.values().toArray()[0].toString(), SkillLevel.valueOf(skill.values().toArray()[1].toString())));
        }
        return skills;
    }

//    randomizing the resources to be returned as response
    private List<UserProfile> limitNoOfResources(List<UserProfile> userProfiles, final int totalItems) {

        Random rand = new Random();
        List<UserProfile> userProfileList = new ArrayList<>();

        for (int i = 0; i < totalItems; i++) {
            int randomIndex = rand.nextInt(userProfiles.size());
            userProfileList.add(userProfiles.get(randomIndex));
            userProfiles.remove(randomIndex);
        }

        return userProfileList;
    }

//    converting the userProfiles to required response
    private List<User> convertUserProfileListToUserList(final List<UserProfile> userProfileList, final String designation) {
        List<User> users = new ArrayList<>();
        for (UserProfile userProfile: userProfileList) {
            users.add(new User(userProfile.getEmail(), userProfile.getName(), designation));
        }
        return users;
    }

}
