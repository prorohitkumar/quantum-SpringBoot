package com.stackroute.nlp.service;


import com.stackroute.nlp.exception.NoProperInput;
import com.stackroute.nlp.exception.NoSearchResult;
import com.stackroute.nlp.model.Project;

import java.util.List;

public interface NLPService {

    List<Project> searchResults(String content) throws NoProperInput, NoSearchResult;

    List<Project> callSearchService(String keyword);

}
