package com.stackroute.resourceallocate.service;

import com.stackroute.resourceallocate.repository.QueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueryServiceImpl implements QueryService
{
    private QueryRepository repository;

    @Autowired
    public QueryServiceImpl(QueryRepository repository) {
        this.repository = repository;

    }

    @Override
    public void createCityNode() {
        repository.createCityNode();
    }

    @Override
    public void createAgeGroupNode() {
        repository.createAgeGroupNode();
    }

    @Override
    public void createProfessionalDomainNode() {
        repository.createProfessionalDomainNode();
    }

    @Override
    public void createTechnicalSkillsNode() {
        repository.createTechnicalSkillsNode();
    }

    @Override
    public void createProfessionalExperienceNode() {
        repository.createProfessionalExperienceNode();
    }

    @Override
    public void createCTCNode() {
        repository.createCTCNode();
    }

    @Override
    public void createDesignationNode() {
        repository.createDesignationNode();
    }


}
