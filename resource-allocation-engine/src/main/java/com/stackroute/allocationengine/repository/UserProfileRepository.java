package com.stackroute.allocationengine.repository;

import com.stackroute.allocationengine.model.UserProfile;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProfileRepository extends Neo4jRepository<UserProfile, String> {

    @Query("match (u:UserProfile {availableForProject: true})-[:LOCATED_IN]->(:City {name: $location}) with u \n" +
            "match (u)-[rel:HAS_EXPERIENCE_OF]->(pe:ProfessionalExperience) where rel.experienceInYrs >= $minExp and rel.experienceInYrs <= $maxExp with u \n" +
            "match (u)-[:WORKS_IN]->(:Designation {name: $designation}) with u \n" +
            "match (u)-[:HAS_WORKED_IN]->(:ProfessionalDomain {name: $domainName}) return u")
    public List<UserProfile> getRelatedUsers(String location, Integer minExp, Integer maxExp, String designation, String domainName);

}
