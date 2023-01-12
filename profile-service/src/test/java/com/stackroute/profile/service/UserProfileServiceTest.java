package com.stackroute.profile.service;

import com.stackroute.profile.model.DomainExperience;
import com.stackroute.profile.model.Skill;
import com.stackroute.profile.model.UserProfile;
import com.stackroute.profile.enums.SkillLevel;
import com.stackroute.profile.enums.UserRole;
import com.stackroute.profile.exception.UserProfileAlreadyExistsException;
import com.stackroute.profile.exception.UserProfileNotFoundException;
import com.stackroute.profile.repository.UserProfileRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class UserProfileServiceTest
{
    @Mock
    private UserProfileRepository userProfileRepository;

    @InjectMocks
    private UserProfileServiceImpl userProfileServiceImpl;

    private UserProfile userProfile;
    private UserProfile updatedUserProfileDetails;
    private List<Skill> skills;
    private List<DomainExperience> domainExperiences;
    private List<String> completedProjects;
    private Optional<UserProfile> optionalUserProfile;
    private Optional<List<UserProfile>> optionalUserProfileList;

    private List<UserProfile> userProfileList;

    @BeforeEach
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);

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


        userProfile=new UserProfile();
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

        optionalUserProfile=Optional.of(userProfile);

        userProfileList=new ArrayList<>();
        userProfileList.add(userProfile);
        optionalUserProfileList= Optional.of(userProfileList);
    }

    @AfterEach
    public void tearDown() throws Exception
    {
        userProfileRepository.deleteAll();
    }

    @Test
    public void addUserProfileSuccess() throws UserProfileAlreadyExistsException
    {
        when(userProfileRepository.insert((UserProfile) any())).thenReturn(userProfile);
        UserProfile newUser= userProfileServiceImpl.addUser(userProfile);
        assertEquals(userProfile,newUser);

    }

    @Test
    public void addUserProfileFailure() throws UserProfileAlreadyExistsException
    {
        when(userProfileRepository.insert(userProfile)).thenReturn(null);
        UserProfile newUser=userProfileServiceImpl.addUser(userProfile);
        assertEquals(null,newUser);
    }

    @Test
    public void getUserProfileDetailsSuccess() throws UserProfileNotFoundException {
        when(userProfileRepository.findById("dummy@tsymphony.in")).thenReturn(optionalUserProfile);
        UserProfile getUserDetails=userProfileServiceImpl.getUserProfileById("dummy@tsymphony.in");
        assertEquals(userProfile,getUserDetails);

    }

    @Test
    public void updateUserDetailsSuccess() throws UserProfileNotFoundException
    {
        when(userProfileRepository.findById("dummy@tsymphony.in")).thenReturn(optionalUserProfile);
        assertThat(userProfile.getEmail(),is(optionalUserProfile.get().getEmail()));
        userProfile.setDesignation("Back end");
        assertThat("Back end",is(userProfile.getDesignation()));
        updatedUserProfileDetails= userProfileServiceImpl.updateUserDetails(userProfile.getEmail(),userProfile);
        assertThat(true,is( userProfile.getDesignation().equals("Back end")));
    }

    @Test
    public void updateUserDetailsFailure()
    {
        when(userProfileRepository.findById("dummy1@tsymphony.in")).thenThrow(NoSuchElementException.class);
        userProfile.setDesignation("Back end");
        assertThrows(UserProfileNotFoundException.class,
                ()->{ userProfileServiceImpl.updateUserDetails("dummy@tsymphony.in",userProfile); });

    }

    @Test
    public void fetchAllUserDetailsSuccess()
    {
        when(userProfileRepository.findAll()).thenReturn(userProfileList);
        List<UserProfile> userProfileList1=userProfileRepository.findAll();
        assertEquals(userProfileList,userProfileList1);
    }

}