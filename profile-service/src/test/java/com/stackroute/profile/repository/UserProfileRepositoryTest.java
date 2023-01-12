package com.stackroute.profile.repository;

import com.stackroute.profile.model.DomainExperience;
import com.stackroute.profile.model.Skill;
import com.stackroute.profile.model.UserProfile;
import com.stackroute.profile.enums.SkillLevel;
import com.stackroute.profile.enums.UserRole;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class UserProfileRepositoryTest
{
    @Autowired
    private UserProfileRepository userProfileRepository;

    private UserProfile userProfile=new UserProfile();
    private List<Skill> skills;
    private List<DomainExperience> domainExperiences;
    private List<String> completedProjects;


    @BeforeEach
    public void setUp() throws Exception
    {
        domainExperiences=new ArrayList<>();
        domainExperiences.add(new DomainExperience("Banking",1.5f));
        domainExperiences.add(new DomainExperience("Retail",0.5f));
        domainExperiences.add(new DomainExperience("Education",0.5f));

        skills=new ArrayList<>();
        skills.add(new Skill("Java", SkillLevel.INTERMEDIATE));
        skills.add(new Skill("JavaScript", SkillLevel.BASIC));
        skills.add(new Skill("HTML", SkillLevel.INTERMEDIATE));
        skills.add(new Skill("Docker", SkillLevel.BASIC));

        completedProjects=new ArrayList<>();
        completedProjects.add("MRF Tyres Ltd");
        completedProjects.add("Quantum");
        completedProjects.add("Football Registration");

        userProfile.setEmail("dummy@tsymphony.in");
        userProfile.setFirstName("Dummy");
        userProfile.setLastName("One");
        userProfile.setPhoneNo("9876543210");
        userProfile.setUserRole(UserRole.RESOURCE);
        userProfile.setLocation("Bangalore");
        userProfile.setAnEmployee(true);
        userProfile.setAvailableForProject(true);
        userProfile.setDesignation("Full Stack");
        userProfile.setCtc(2500000f);
        userProfile.setExperienceInYrs(5f);
        userProfile.setDomainExperiences(domainExperiences);
        userProfile.setSkills(skills);
        userProfile.setCompletedProjects(completedProjects);

    }

    @AfterEach
    public void tearDown() throws Exception
    {
        userProfileRepository.deleteAll();
    }

    @Test
    public void addUserProfileTest()
    {
        userProfileRepository.insert(userProfile);
        UserProfile user=userProfileRepository.findById("dummy@tsymphony.in").get();
        assertThat(userProfile.getEmail(),is(user.getEmail()));
    }

    @Test
    public void updateUserProfileDetailTest()
    {
        userProfileRepository.insert(userProfile);
        UserProfile user=userProfileRepository.findById("dummy@tsymphony.in").get();
        assertThat(userProfile.getEmail(),is(user.getEmail()));

        user.setCtc(500000.76f);
        userProfileRepository.save(user);

        user= userProfileRepository.findById("dummy@tsymphony.in").get();
        assertThat(500000.76f,is(user.getCtc()));

    }

    @Test
    public void getUserTest()
    {
        userProfileRepository.insert(userProfile);
        UserProfile user=userProfileRepository.findById("dummy@tsymphony.in").get();
        assertThat(userProfile.toString(),is(user.toString()));
    }

    @Test
    public void getAllUsersTest()
    {
        userProfileRepository.insert(userProfile);
        List<UserProfile> userProfiles=userProfileRepository.findAll();
        assertThat(userProfiles.size(),is(1));
    }

}
