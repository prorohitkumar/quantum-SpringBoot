package com.stackroute.resourceallocate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity(label = "TechnicalSkill")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TechnicalSkill
{
    private String name;
}
