package com.stackroute.project.repository;

import com.stackroute.project.model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ProjectRepository extends MongoRepository<Project, String> {
}
