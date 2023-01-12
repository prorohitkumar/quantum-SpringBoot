package com.stackroute.resourceallocate.controller;

import com.stackroute.resourceallocate.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/resource/query/node")
public class QueryController
{
    private QueryService service;

    @Autowired
    public QueryController(QueryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> nodeCreated()
    {
        service.createCityNode();
        service.createAgeGroupNode();
        service.createProfessionalDomainNode();
        service.createTechnicalSkillsNode();
        service.createProfessionalExperienceNode();
        service.createCTCNode();
        service.createDesignationNode();
        return new ResponseEntity<>("nodes are created", HttpStatus.OK);
    }
}
