package com.stackroute.profile.controller;

import com.stackroute.profile.convertor.UserProfileConvertor;
import com.stackroute.profile.dto.UserDTO;
import com.stackroute.profile.model.UserProfile;
import com.stackroute.profile.enums.UserRole;
import com.stackroute.profile.exception.UserProfileAlreadyExistsException;
import com.stackroute.profile.exception.UserProfileNotFoundException;
import com.stackroute.profile.service.UserProfileService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@CrossOrigin(origins="*")
@RestController
@RequestMapping("api/v1/userprofile")
public class UserProfileController
{
    private UserProfileService userProfileService;
    private UserProfileConvertor convertor;
    private RabbitTemplate template;

    @Value("${spring.rabbitmq.userExchange}")
    public String USER_EXCHANGE;

    @Value("${spring.rabbitmq.userAuthRoutingkey}")
    public String USER_AUTH_ROUTING_KEY;

    @Value("${spring.rabbitmq.userSearchRoutingkey}")
    public String USER_SEARCH_ROUTING_KEY;

    @Value("${spring.rabbitmq.userResourceAllocationRoutingKey}")
    public String USER_ALLOCATE_ROUTING_KEY;

    @Autowired
    public UserProfileController(UserProfileService userProfileService, UserProfileConvertor convertor,
                                 RabbitTemplate template)
    {
        this.userProfileService = userProfileService;
        this.convertor = convertor;
        this.template=template;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO user) throws UserProfileAlreadyExistsException
    {
        try
        {
            if(user.getUserRole()==null)
            {
                user.setUserRole(UserRole.RESOURCE);
                user.setAction("create");
            }
            else
            {
                user.setUserRole(UserRole.MANAGER);
                user.setAction("create");
            }

            //passing the rabbitmq object
            template.convertAndSend(USER_EXCHANGE, USER_AUTH_ROUTING_KEY,user);
            template.convertAndSend(USER_EXCHANGE, USER_SEARCH_ROUTING_KEY, user);
            template.convertAndSend(USER_EXCHANGE, USER_ALLOCATE_ROUTING_KEY,user);

            UserProfile userResult=convertor.dtoToEntity(user);

            UserProfile newUserAdded=userProfileService.addUser(userResult);

            return new ResponseEntity<>(newUserAdded,HttpStatus.CREATED);
        }
        catch (UserProfileAlreadyExistsException existsException)
        {
            return new ResponseEntity<String>("User already exists",HttpStatus.CONFLICT);
        }

    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUserProfileDetails(@RequestParam("email") String email,@RequestBody UserProfile userProfile) throws UserProfileNotFoundException
    {
        try
        {
            UserProfile updatedUserProfile= userProfileService.updateUserDetails(email,userProfile);

            UserDTO userDTO = new UserDTO(userProfile.getEmail(), null, userProfile.getFirstName(), userProfile.getLastName(),
                    userProfile.getPhoneNo(), userProfile.getGender(), userProfile.getDateOfBirth().toString(), userProfile.getUserRole(), userProfile.getLocation(),
                    userProfile.isAnEmployee(), userProfile.isAvailableForProject(), userProfile.isShowNotification(), userProfile.getDesignation(), userProfile.getCtc(), userProfile.getExperienceInYrs(),
                    userProfile.getDomainExperiences(), userProfile.getSkills(), userProfile.getCompletedProjects(), "update");

            //passing the rabbitmq object
            template.convertAndSend(USER_EXCHANGE, USER_AUTH_ROUTING_KEY,userDTO);
            template.convertAndSend(USER_EXCHANGE, USER_SEARCH_ROUTING_KEY, userDTO);
            template.convertAndSend(USER_EXCHANGE, USER_ALLOCATE_ROUTING_KEY,userDTO);


            return new ResponseEntity<>(updatedUserProfile,HttpStatus.OK);
        }
        catch (UserProfileNotFoundException notFoundException)
        {
            return new ResponseEntity<String>("User profile not found",HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/fetchuser")
    public ResponseEntity<?> fetchRegisteredUser(@RequestParam("email") String email) throws UserProfileNotFoundException
    {
        try
        {
            return new ResponseEntity<>(userProfileService.getUserProfileById(email),HttpStatus.OK);
        }
        catch (UserProfileNotFoundException notFoundException)
        {
            return new ResponseEntity<String>("User profile not found",HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/fetchusers")
    public ResponseEntity<?> fetchAllRegisteredUsers()
    {
        return new ResponseEntity<>(userProfileService.getAllUsers(),HttpStatus.OK);
    }

    @GetMapping("/status")
    public ResponseEntity<?> getProfileStatus(@RequestParam("email") String email) {
        try {
            return new ResponseEntity<>(userProfileService.getProfileStatus(email), HttpStatus.OK);
        } catch (UserProfileNotFoundException notFoundException) {
            return new ResponseEntity<String>("User profile not found",HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/upload")
    public ResponseEntity<?> addPhoto( @RequestParam("email") String email,@RequestParam("image") String multipart) throws UserProfileNotFoundException, IOException {
        try {
            UserProfile userProfile = userProfileService.addPhoto(email,multipart);
//            System.out.println("hello");
//            System.out.println(userProfile);
            return new ResponseEntity<UserProfile>(userProfile, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
}
