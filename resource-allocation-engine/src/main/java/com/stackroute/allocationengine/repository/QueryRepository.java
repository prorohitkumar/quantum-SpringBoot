package com.stackroute.allocationengine.repository;

import com.stackroute.allocationengine.enums.SkillLevel;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface QueryRepository extends Neo4jRepository {

    @Query("match (u: UserProfile)-[r:HAS_SKILL]->(s:TechnicalSkills) where u.email = $email return s.name as skillName, r.level as level")
    public List<Map<String, SkillLevel>> getSkillsOfUser(String email);

    @Query("match (u:UserProfile {email: $email})-[rel:HAS_SKILL]->(ts:TechnicalSkills {name: $requiredSkillName})\n" +
            "where rel.level in $requiredList \n" +
            "return u.name")
    public String getUserWithRequiredSkillAndLevel(String email, String requiredSkillName, List<String> requiredList);

}
