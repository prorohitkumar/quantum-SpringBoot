package com.stackroute.resourceallocate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity(label = "AgeGroup")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Age
{
    private String name;
    private int min_age;
    private int max_age;
}
