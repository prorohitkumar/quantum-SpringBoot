package com.stackroute.nlp.service;

import com.stackroute.nlp.model.Project;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "search-service", url = "http://localhost:8087")
public interface SearchServiceClient {

    @GetMapping("/api/v1/projects/search")
    List<Project> searchResponse(@RequestParam("value") String keyword);

}
