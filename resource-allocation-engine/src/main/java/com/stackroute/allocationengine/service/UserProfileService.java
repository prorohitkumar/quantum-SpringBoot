package com.stackroute.allocationengine.service;

import com.stackroute.allocationengine.dto.ProjectDTO;
import com.stackroute.allocationengine.dto.ProjectRecommendationResponse;
import com.stackroute.allocationengine.exception.ResourcesNotSufficientException;
import com.stackroute.allocationengine.model.UserProfile;

import java.util.List;

public interface UserProfileService {

    public List<ProjectRecommendationResponse> getRecommendedUsers(ProjectDTO projectDTO) throws ResourcesNotSufficientException;

}
