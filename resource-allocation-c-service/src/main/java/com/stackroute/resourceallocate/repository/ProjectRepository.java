package com.stackroute.resourceallocate.repository;

import com.stackroute.resourceallocate.model.Project;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

/*
    This repository will create the relationships between the Project node and other nodes present in
    the neo4j database.
 */

@Repository
public interface ProjectRepository extends Neo4jRepository<Project,String>
{
    /*
      This method is used to find out the project by the name.
     */
    Project findByName(String projectName);

    /*
     This method will create the relationship between Project, City and Professional Domain nodes having relationships
     LOCATED_IN between Project and City
     */
    @Query( "MATCH (p:Project) where p.projectId=$projectId with p \n" +
            "MATCH (l:City) where l.name=$location \n" +
            "MERGE (p)-[:LOCATED_IN]->(l)")
    public void createRelationshipForProjectAndLocation(String projectId,String location);

    /*
     This method will create the relationship between Project and Professional
     Domain nodes having relationships DEALS_IN
     */
    @Query("MATCH (p:Project) where p.projectId=$projectId with p \n" +
            "MATCH (pd:ProfessionalDomain) where pd.name=$domainName \n" +
            "MERGE (p)-[:DEALS_IN]->(pd)")
    public void createRelationshipForProjectAndDomain(String projectId,String domainName);
    /*
    This method will create relationship between Project and TechnicalSkills denoting relationship
    REQUIRES_SKILL with a property level which will define the level of skill required as BASIC, INTERMEDIATE,
    ADVANCE.
     */
    @Query( "MATCH (p:Project) where p.projectId=$projectId with p \n" +
            "MATCH(t:TechnicalSkills) WHERE t.name=$skillName  \n" +
            "MERGE (p)-[:REQUIRES_SKILL{level:$level}]->(t)")
    public void createRelationshipForProjectAndSkillsNode(String projectId,String skillName, String level);

    /*
       This method delete the previous relationships between the nodes and be would used to update the new
       relationships between the nodes.
     */
    @Query("Match (p:Project {projectId:$projectId})-[r]->(b) delete r")
    public void removeRelationships(String projectId);
}