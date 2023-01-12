package com.stackroute.profile.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.stackroute.profile.model.DomainExperience;
import com.stackroute.profile.model.Skill;
import com.stackroute.profile.model.UserProfile;
import com.stackroute.profile.enums.SkillLevel;
import com.stackroute.profile.enums.UserRole;
import com.stackroute.profile.service.UserProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserProfileControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private UserProfile userProfile;

//    @MockBean
//    User user;


    @MockBean
    private UserProfileService userProfileService;

    @InjectMocks
    private UserProfileControllerForTesting userProfileController;

    private List<Skill> skills;
    private List<DomainExperience> domainExperiences;
    private List<String> completedProjects;
    private List<UserProfile> userProfileList;
    private Optional<UserProfile> optionalUserProfile;
    private Optional<List<UserProfile>> optionalUserProfileList;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userProfileController).build();

        domainExperiences = new ArrayList<>();
        domainExperiences.add(new DomainExperience("Banking", 1.5f));
        domainExperiences.add(new DomainExperience("Retail", 0.5f));
        domainExperiences.add(new DomainExperience("Education", 0.5f));

        skills = new ArrayList<>();
        skills.add(new Skill("Java", SkillLevel.INTERMEDIATE));
        skills.add(new Skill("JavaScript", SkillLevel.BASIC));
        skills.add(new Skill("HTML", SkillLevel.INTERMEDIATE));
        skills.add(new Skill("Docker", SkillLevel.BASIC));

        completedProjects = new ArrayList<>();
        completedProjects.add("MRF Tyres Ltd");
        completedProjects.add("Quantum");
        completedProjects.add("Football Registration");

        userProfile = new UserProfile();
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

        optionalUserProfile = Optional.of(userProfile);

        userProfileList = new ArrayList<>();
        userProfileList.add(userProfile);
        optionalUserProfileList = Optional.of(userProfileList);
    }


    @Test
    public void addUser() throws Exception
    {
        when(userProfileService.addUser(any())).thenReturn(userProfile);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/test/userprofile/register").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userProfile)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    public void updateUserDetails() throws Exception {
        when(userProfileService.updateUserDetails(eq("dummy@tsymphony.in"),any())).thenReturn(userProfile);
        userProfile.setDesignation("Back end");
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/test/userprofile/update?email=dummy@tsymphony.in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userProfile)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    private static String asJsonString(final Object obj)
    {
        try {
            ObjectMapper objmapper = new ObjectMapper();
            objmapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objmapper.registerModule(new JavaTimeModule());
            return objmapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

}
