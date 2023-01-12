package com.stackroute.allocationengine.controller;

import com.stackroute.allocationengine.dto.ProjectDTO;
import com.stackroute.allocationengine.exception.ResourcesNotSufficientException;
import com.stackroute.allocationengine.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
public class RecommendationController {

    private UserProfileService userProfileService;

    @Autowired
    public RecommendationController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @PostMapping("/userSuggestions")
    public ResponseEntity<?> getRecommendedUsers(@RequestBody ProjectDTO projectDTO) {
        try {
            return new ResponseEntity<>(userProfileService.getRecommendedUsers(projectDTO), HttpStatus.OK);
        } catch (ResourcesNotSufficientException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Sufficient number of resources are not present", HttpStatus.NOT_FOUND);
        }
    }

}
