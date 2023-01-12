package com.stackroute.resourceallocate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity(label = "UserProfile")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile
{
    @Id
    private String email;
    private String name;
    private boolean availableForProject;

}
