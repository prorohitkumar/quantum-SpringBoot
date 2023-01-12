package com.stackroute.allocationengine.model;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
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
