package com.stackroute.search.repository;

import com.stackroute.search.model.UserProfile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends ElasticsearchRepository<UserProfile, String> {
}
