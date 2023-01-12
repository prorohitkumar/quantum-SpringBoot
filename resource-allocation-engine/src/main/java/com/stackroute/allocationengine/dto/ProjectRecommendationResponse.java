package com.stackroute.allocationengine.dto;

import com.stackroute.allocationengine.model.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProjectRecommendationResponse {
    private String designation;
    private int noOfResources;
    private List<User> resourcesSuggested;
}
