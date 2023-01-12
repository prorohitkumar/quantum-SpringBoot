package com.stackroute.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.stackroute.project.converter.ProjectConverter;
import com.stackroute.project.dto.ProjectDTO;
import com.stackroute.project.enums.ProjectStatus;
import com.stackroute.project.exception.ProjectAlreadyExistsException;
import com.stackroute.project.exception.ProjectNotFoundException;
import com.stackroute.project.model.Manager;
import com.stackroute.project.model.Project;
import com.stackroute.project.service.ProjectServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
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

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTest {

    private MockMvc mockMvc;
    @MockBean
    private ProjectServiceImpl projectService;
    @InjectMocks
    private ProjectController projectController;

    private Project project;


    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();

        project = new Project();
        project.setProjectId("PR_1");
        project.setProjectName("First Project");
        project.setProjectDescription("Some basic attributes");
//        project.setStartDate(LocalDate.of(2021,10,10));
        project.setProjectStatus(ProjectStatus.NOT_STARTED);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void addProjectSuccess() throws Exception {
        Mockito.when(projectService.addProject(Mockito.any())).thenReturn(project);
        Mockito.when(projectService.isTesting()).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/project").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(project)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void addProjectFailure() throws Exception {
        Mockito.when(projectService.addProject(Mockito.any()))
                .thenThrow(new ProjectAlreadyExistsException("Project Already Exists"));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/project").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(project)))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateProjectSuccess() throws Exception {
        Manager manager = new Manager();
        manager.setEmail("sam.manager@gmail.com");
        manager.setFirstName("Sameer");
        manager.setLastName("Saxena");
        project.setManager(manager);

        Mockito.when(projectService.updateProject(project.getProjectId(),project)).thenReturn(project);
        Mockito.when(projectService.isTesting()).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/project/PR_1").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(project)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateProjectFailure() throws Exception {
        Mockito.when(projectService.updateProject(project.getProjectId(),project))
                .thenThrow(new ProjectNotFoundException("Project Not Found Exception."));
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/project/PR_1").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(project)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getProjectByIdSuccess() throws Exception {
        Mockito.when(projectService.getProjectById(Mockito.any())).thenReturn(project);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/project").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(project))
                .param("projectId", "PR_1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getProjectByIdFailure() throws Exception {
        Mockito.when(projectService.getProjectById(Mockito.any()))
                .thenThrow(new ProjectNotFoundException("Project Not Found Exception."));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/project").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(project))
                .param("projectId", "PR_1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    private static String asJsonString(final Object obj) {
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