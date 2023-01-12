package com.stackroute.allocationengine.repository;

import com.stackroute.allocationengine.model.Project;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends Neo4jRepository<Project, String> {
}
