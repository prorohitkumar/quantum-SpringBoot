package com.stackroute.project.controller;

import com.stackroute.project.EmailTemplate;
import com.stackroute.project.QRTemplate;
import com.stackroute.project.converter.ProjectConverter;
import com.stackroute.project.dto.ProjectDTO;
import com.stackroute.project.exception.ProjectAlreadyExistsException;
import com.stackroute.project.exception.ProjectNotFoundException;
import com.stackroute.project.model.Project;
import com.stackroute.project.model.UserProfile;
import com.stackroute.project.service.NotificationService;
import com.stackroute.project.service.ProjectService;
import com.stackroute.project.service.QRGenerationService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.*;
import java.util.List;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("api/v1")
public class ProjectController {

    @Autowired
    public NotificationService emailService;
    @Autowired
    public QRGenerationService qrService;
    private ProjectService projectService;
    private ProjectConverter projectConverter;
    private RabbitTemplate rabbitTemplate;


    @Value("${spring.rabbitmq.exchange}")
    public String EXCHANGE;

    @Value("${spring.rabbitmq.projectProfileRoutingkey}")
    public String PROJECT_PROFILE_ROUTING_KEY;

    @Value("${spring.rabbitmq.projectProfileListRoutingKey}")
    public String PROJECT_PROFILE_LIST_ROUTING_KEY;

    @Value("${spring.rabbitmq.projectSearchRoutingkey}")
    public String PROJECT_SEARCH_ROUTING_KEY;

    @Value("${spring.rabbitmq.projectAllocateRoutingKey}")
    public String PROJECT_ALLOCATE_ROUTING_KEY;

    @Autowired
    public ProjectController(ProjectService projectService, ProjectConverter projectConverter, RabbitTemplate rabbitTemplate) {
        this.projectService = projectService;
        this.projectConverter = projectConverter;
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/project")
    public ResponseEntity<?> addProject(@RequestBody Project project) throws ProjectAlreadyExistsException, ProjectNotFoundException {

        QRTemplate qrTemplate = new QRTemplate("qrgenerateddata.html");
        Map<String, String> qrreplacements = new HashMap<String, String>();


        Project newProject = projectService.addProject(project);
        ResponseEntity<?> response = new ResponseEntity<>(newProject, HttpStatus.CREATED);

        qrreplacements.put("projectId", project.getProjectId());
        qrreplacements.put("projectName", project.getProjectName());
        qrreplacements.put("projectDescription", project.getProjectDescription());
        qrreplacements.put("projectLocation", project.getLocation());
//        qrreplacements.put("projectStartDate", project.getStartDate().toString());
        qrreplacements.put("projectDuration", ((Integer) project.getDurationInWeeks()).toString());
        qrreplacements.put("projectStatus", project.getProjectStatus().name());
        qrreplacements.put("projectManager", project.getManager().getFirstName() +" "+ project.getManager().getLastName());
        qrreplacements.put("projectManagerEmail", project.getManager().getEmail());
        String qrMessage=  qrTemplate.getTemplate(qrreplacements);
        byte[] qrByteArray = this.qrService.generateQR(qrMessage, 200, 200);
        String encodedQRString = Base64.getEncoder().encodeToString(qrByteArray);
        project.setProjectQR(encodedQRString);

        projectService.updateProject(newProject.getProjectId(), project);




        /*Producing RabbitMQ*/

        /*Condition for JUnit Test purposes*/
        if(!projectService.isTesting()){
            ProjectDTO projectDTO = projectConverter.entityToDTO("create", project);
            rabbitTemplate.convertAndSend(EXCHANGE, PROJECT_PROFILE_ROUTING_KEY, projectDTO);
            rabbitTemplate.convertAndSend(EXCHANGE, PROJECT_SEARCH_ROUTING_KEY, projectDTO);
            rabbitTemplate.convertAndSend(EXCHANGE, PROJECT_ALLOCATE_ROUTING_KEY, projectDTO);
        }

        return response;
    }

    @PutMapping("/project/{projectId}")
    public ResponseEntity<?> updateProject(@PathVariable String projectId, @RequestBody Project project) throws ProjectNotFoundException, MessagingException, IOException {
        project.setProjectId(projectId);
        Project existingProject = projectService.updateProject(projectId, project);
        ResponseEntity<?> response = new ResponseEntity<>(existingProject, HttpStatus.OK);

        QRTemplate qrTemplate = new QRTemplate("qrgenerateddata.html");
        Map<String, String> qrreplacements = new HashMap<String, String>();

        qrreplacements.put("projectId", project.getProjectId());
        qrreplacements.put("projectName", project.getProjectName());
        qrreplacements.put("projectDescription", project.getProjectDescription());
        qrreplacements.put("projectLocation", project.getLocation());
//        qrreplacements.put("projectStartDate", project.getStartDate().toString());
        qrreplacements.put("projectDuration", ((Integer) project.getDurationInWeeks()).toString());
        qrreplacements.put("projectStatus", project.getProjectStatus().name());
        qrreplacements.put("projectManager", project.getManager().getFirstName() +" "+ project.getManager().getLastName());
        qrreplacements.put("projectManagerEmail", project.getManager().getEmail());
        String qrMessage=  qrTemplate.getTemplate(qrreplacements);
        byte[] qrByteArray = this.qrService.generateQR(qrMessage, 200, 200);
        String encodedQRString = Base64.getEncoder().encodeToString(qrByteArray);
        project.setProjectQR(encodedQRString);

        projectService.updateProject(project.getProjectId(), project);

        /*Producing RabbitMQ*/

        /*Condition for JUnit Test purposes*/
        if(!projectService.isTesting()){
            ProjectDTO projectDTO = projectConverter.entityToDTO("update", project);
            rabbitTemplate.convertAndSend(EXCHANGE, PROJECT_PROFILE_ROUTING_KEY, projectDTO);
            rabbitTemplate.convertAndSend(EXCHANGE, PROJECT_SEARCH_ROUTING_KEY, projectDTO);
            rabbitTemplate.convertAndSend(EXCHANGE, PROJECT_ALLOCATE_ROUTING_KEY, projectDTO);

            if(projectDTO.isTeamCreated()==true)
            {   List<String> profileIdList = new ArrayList<String>();
                for(UserProfile userProfile: projectDTO.getAllocatedResources())
                {
                    profileIdList.add(userProfile.getEmail());

                    EmailTemplate emailTemplate = new EmailTemplate("notificationemail.html");

                    Map<String, String> replacements = new HashMap<String, String>();
                    replacements.put("projectId", project.getProjectId());
                    replacements.put("projectName", project.getProjectName());
//                    replacements.put("projectStartDate", project.getStartDate().toString());
                    replacements.put("projectDuration", Integer.toString(project.getDurationInWeeks()));
                    replacements.put("resourceName", userProfile.getFirstName() + " " + userProfile.getLastName());
                    replacements.put("resourceEmail", userProfile.getEmail());
                    replacements.put("resourceDesignation", userProfile.getDesignation());
                    replacements.put("managerName", project.getManager().getFirstName()+" "+ project.getManager().getLastName());
                    replacements.put("managerEmail", project.getManager().getEmail());

                    String message = emailTemplate.getTemplate(replacements);

                    emailService.sendNotificationEmail(userProfile.getEmail(), "Quantum Project Selection", message);


                }
                rabbitTemplate.convertAndSend(EXCHANGE, PROJECT_PROFILE_LIST_ROUTING_KEY, profileIdList);
            }
        }

        return response;
    }

    @GetMapping("/project")
    public ResponseEntity<?> getProjectById(@RequestParam("projectId") String projectId) throws ProjectNotFoundException {
        Project project = projectService.getProjectById(projectId);
        ResponseEntity<?> response = new ResponseEntity<>(project, HttpStatus.OK);
        return response;
    }

    @GetMapping("/projects")
    public ResponseEntity<?> getAllProjects(@RequestParam(name = "location", required = false) String location,
                                            @RequestParam(name = "managerEmail", required = false) String managerEmail,
                                            @RequestParam(name = "projectStatus", required = false)String projectStatus){
        List<Project> projects = projectService.getAllProjects(location, managerEmail, projectStatus);
        ResponseEntity<?> response = new ResponseEntity<>(projects, HttpStatus.OK);
        return response;
    }


}
