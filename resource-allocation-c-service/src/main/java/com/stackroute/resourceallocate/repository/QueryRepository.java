package com.stackroute.resourceallocate.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

/*
    This repository is used to load csv data  from the file present in resources/nodecreation.csv and create
     the respective nodes for the Labels City, CTC, AgeGroup, ProfessionalDomain, ProfessionalExperience,
     TechnicalSkills and Designation in neo4j database.
 */

@Repository
public interface QueryRepository extends Neo4jRepository
{
    /*
     * This method will create the node with the Label City
     */
    @Query("load csv with headers from \"file:///nodecreation.csv\" as row with row\n" +
            "WHERE row.City IS NOT NULL \n" +
            "MERGE (c:City {name:row.City})\n")
    public void createCityNode();

    /*
     * This method will create the node with the Label AgeGroup
     */
    @Query("load csv with headers from \"file:///nodecreation.csv\" as row with row\n" +
            "WHERE row.AgeGroup IS NOT NULL AND row.MinAge IS NOT NULL AND row.MaxAge IS NOT NULL\n" +
            "MERGE (a:AgeGroup{name:row.AgeGroup,min_age:row.MinAge,max_age:row.MaxAge})")
    public void createAgeGroupNode();

    /*
     * This method will create the node with the Label ProfessionalDomain
     */
    @Query("load csv with headers from \"file:///nodecreation.csv\" as row with row\n" +
            "WHERE row.ProfessionalDomain IS NOT NULL \n" +
            "MERGE (pd:ProfessionalDomain{name:row.ProfessionalDomain})")
    public void createProfessionalDomainNode();

    /*
     * This method will create the node with the Label TechnicalSkill
     */
    @Query("load csv with headers from \"file:///nodecreation.csv\" as row with row\n" +
            "WHERE row.TechnicalSkills IS NOT NULL\n" +
            "MERGE (ts:TechnicalSkills{name:row.TechnicalSkills})")
    public void createTechnicalSkillsNode();

    /*
     * This method will create the node with the Label ProfessionalExperience
     */
    @Query("load csv with headers from \"file:///nodecreation.csv\" as row with row\n" +
            "WHERE row.ProfessionalExperience IS NOT NULL AND " +
            "row.MinProfessionalExperience IS NOT NULL AND " +
            "row.MaxProfessionalExperience IS NOT NULL\n" +
            "MERGE (pe:ProfessionalExperience{name:row.ProfessionalExperience," +
            "min_exp:row.MinProfessionalExperience,max_exp:row.MaxProfessionalExperience})")
    public void createProfessionalExperienceNode();

    /*
     * This method will create the node with the Label CTC
     */
    @Query("load csv with headers from \"file:///nodecreation.csv\" as row with row\n" +
            "WHERE row.CTC IS NOT NULL\n" +
            "MERGE(:CTC{name:toInteger(row.CTC)})")
    public void createCTCNode();

    /*
     * This method will create the node with the Label Designation
     */
    @Query("load csv with headers from \"file:///nodecreation.csv\" as row with row\n" +
            "WHERE row.Designation IS NOT NULL \n" +
            "MERGE(:Designation{name:row.Designation})")
    public void createDesignationNode();

}
