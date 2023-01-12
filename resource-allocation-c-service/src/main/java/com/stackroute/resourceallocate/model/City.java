package com.stackroute.resourceallocate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity(label = "City")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class City
{
    private String name;
}
