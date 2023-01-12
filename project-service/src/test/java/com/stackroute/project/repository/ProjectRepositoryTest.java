package com.stackroute.project.repository;

import com.stackroute.project.enums.ProjectStatus;
import com.stackroute.project.model.Manager;
import com.stackroute.project.model.Project;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    Project project = new Project();

    @BeforeEach
    public void setUp() throws Exception{
        project.setProjectId("PR_2");
        project.setProjectName("First Project");
        project.setProjectDescription("Some basic attributes");
//        project.setStartDate(LocalDate.of(2021,10,10));
        project.setProjectStatus(ProjectStatus.NOT_STARTED);
    }

    @AfterEach
    public void tearDown() throws  Exception{
        projectRepository.deleteAll();
    }

    @Test
    public void addProjectTest(){
        projectRepository.insert(project);
        Project existingProject = projectRepository.findById("PR_2").get();
        assertEquals(project, existingProject);
    }

    @Test
    public void updateProjectTest(){
        projectRepository.insert(project);
        Manager manager = new Manager();
        manager.setEmail("sam.manager@gmail.com");
        manager.setFirstName("Sameer");
        manager.setLastName("Saxena");

        project.setManager(manager);

        assertEquals(project.getManager(), manager);
    }

    @Test
    public void getProjectByIdTest(){
        projectRepository.insert(project);
        Project existingProject = projectRepository.findById("PR_2").get();
        assertEquals("PR_2", project.getProjectId());
    }

    @Test
    public void getAllProjectsTest(){
        Project secondProject = new Project();
        secondProject.setProjectId("PR_3");

        projectRepository.insert(project);
        projectRepository.insert(secondProject);

        List<Project> projects = projectRepository.findAll();

        assertEquals(projects.size(), 2);
    }

}