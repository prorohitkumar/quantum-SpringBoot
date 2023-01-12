package com.stackroute.resourceallocate.model;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Expertise {

    private int noOfResources;
    private String designation;
    private int minExperienceNeeded;
    private int maxExperienceNeeded;
    private int minAge;
    private int maxAge;
    private String domainExperience;
    private float minCTC;
    private float maxCTC;
    private List<Skill> skills;

}
