package com.stackroute.resourceallocate.repository;

import com.stackroute.resourceallocate.model.UserProfile;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

/*
    This repository will create the relationships between the UserProfile node and other nodes having its
    properties which are fetched from the RabbitMq.
 */

@Repository
public interface UserProfileRepository extends Neo4jRepository<UserProfile,String>
{
    /*
      This method will return the username node when called and find the node with parameter name.
     */
    UserProfile findByName(String name);

    /*
        This method is used to create the relationship HAS_CTC between UserProfile node and CTC node,
        LOCATED_IN between UserProfile node and City node,
        WORKS_IN between UserProfile node and Designation node
     */
    @Query("MATCH (u:UserProfile) where u.email=$email with u \n" +
            "MATCH (c:CTC) where c.name=$ctc \n" +
            "MERGE (u)-[:HAS_CTC]->(c) with u \n" +
            "MATCH (l:City) where l.name=$location \n" +
            "MERGE (u)-[:LOCATED_IN]->(l) with u \n" +
            "MATCH (d:Designation) Where d.name=$designation \n" +
            "MERGE (u)-[:WORKS_IN]->(d)")
    public void createRelationBetweenUserProfileAndOtherNodes
    (String email, Float ctc,String location,String designation);

    /*
       This method will create the relationship HAS_SKILL having skill level as property which can be stated as
       BASIC, INTERMEDIATE, ADVANCE between UserProfile and TechnicalSkills nodes
     */
    @Query("Match(u:UserProfile)where u.email=$email with u \n" +
            "MATCH(t:TechnicalSkills) WHERE t.name=$skillName  \n " +
            "MERGE (u)-[:HAS_SKILL{level: $level }]->(t) ")
    public void createRelationUserAndTechnicalSkill(String email,String skillName, String level);

    /*
       This method will create the relationship HAS_WORKED_IN having domainExperienceInYrs as property
       which can stated as relevant experience in that domain between UserProfile and ProfessionalDomain nodes
     */
    @Query("MATCH (u:UserProfile) where u.email=$email with u \n" +
            "MATCH(pd:ProfessionalDomain ) where pd.name=$domainName \n" +
            "MERGE (u)-[:HAS_WORKED_IN{domainExperienceInYrs: $domainExperienceInYrs }]->(pd)")
    public void createRelationUserAndProfessionalDomain(String email,String domainName, float domainExperienceInYrs );

    /*
        This method will create relationship HAS_EXPERIENCE_OF having property experienceInYrs which
        can be stated as total experience a resource is having in his career.
     */
    @Query("MATCH (u:UserProfile) where u.email=$email with u \n" +
            "MATCH (pe:ProfessionalExperience) where pe.name=$exprTag \n" +
            "MERGE (u)-[:HAS_EXPERIENCE_OF{experienceInYrs:$experienceInYrs}]->(pe)")
    public void createRelationshipUserAndProfessionalExperience(String email,String exprTag,float experienceInYrs);

    /*
        This method delete the previous relationships between the nodes and be would used to update the new
        relationships between the nodes.
     */
    @Query("Match (u:UserProfile {email:$email})-[r]->(b) delete r")
    public void removeRelationships(String email);
}
