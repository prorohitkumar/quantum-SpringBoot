package com.stackroute.project.service;

import com.stackroute.project.enums.ProjectStatus;
import com.stackroute.project.exception.ProjectAlreadyExistsException;
import com.stackroute.project.exception.ProjectNotFoundException;
import com.stackroute.project.model.Manager;
import com.stackroute.project.model.Project;
import com.stackroute.project.repository.ProjectRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProjectServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;
    @InjectMocks
    private ProjectServiceImpl projectService;

    private Project project;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);

        project = new Project();
        project.setProjectId("PR_2");
        project.setProjectName("First Project");
        project.setProjectDescription("Some basic attributes");
//        project.setStartDate(LocalDate.of(2021,10,10));
        project.setProjectStatus(ProjectStatus.NOT_STARTED);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void addProjectSuccess() throws ProjectAlreadyExistsException {
        Mockito.when(projectRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        Mockito.when(projectRepository.save(Mockito.any())).thenReturn(project);

        Project addProject = projectService.addProject(project);

        assertEquals(project, addProject);

    }

    @Test
    public void addProjectFailure(){
        Mockito.when(projectRepository.findById((Mockito.any()))).thenReturn(Optional.of(project));
        assertThrows(ProjectAlreadyExistsException.class, () -> projectService.addProject(project));
    }

    @Test
    public void updateProjectSuccess() throws ProjectNotFoundException {
        Mockito.when(projectRepository.findById((Mockito.any()))).thenReturn(Optional.of(project));

        Manager manager = new Manager();
        manager.setEmail("sam.manager@gmail.com");
        manager.setFirstName("Sameer");
        manager.setLastName("Saxena");
        project.setManager(manager);

        Mockito.when(projectRepository.save(Mockito.any())).thenReturn(project);

        assertEquals(project, projectService.updateProject(project.getProjectId(), project));
    }

    @Test
    public void updateProjectFailure(){
        Mockito.when(projectRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        assertThrows(ProjectNotFoundException.class, () -> projectService.updateProject(project.getProjectId(), project));
    }

    @Test
    public void getProjectByIdSuccess() throws ProjectNotFoundException {
        Mockito.when(projectRepository.findById((Mockito.any()))).thenReturn(Optional.of(project));
        assertEquals(project, projectService.getProjectById(project.getProjectId()));
    }

    @Test
    public void getProjectByIdFailure(){
        Mockito.when(projectRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        assertThrows(ProjectNotFoundException.class, () -> projectService.getProjectById(project.getProjectId()));
    }


}









