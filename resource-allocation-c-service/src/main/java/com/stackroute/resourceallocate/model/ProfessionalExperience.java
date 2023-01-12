package com.stackroute.resourceallocate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity(label = "ProfessionalExperience")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfessionalExperience
{
    String name;
    float min_exp;
    float max_exp;
}
