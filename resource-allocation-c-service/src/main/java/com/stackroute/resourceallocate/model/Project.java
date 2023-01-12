package com.stackroute.resourceallocate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity(label="Project")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project
{
    @Id
    private String projectId;
    private String name;
    private String projectDescription;
}
