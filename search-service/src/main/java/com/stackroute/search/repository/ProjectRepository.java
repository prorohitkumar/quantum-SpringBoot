package com.stackroute.search.repository;

import com.stackroute.search.model.Project;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends ElasticsearchRepository<Project, String> {
}
