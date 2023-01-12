package com.stackroute.nlp.controller;

import com.stackroute.nlp.exception.NoProperInput;
import com.stackroute.nlp.exception.NoSearchResult;
import com.stackroute.nlp.service.NLPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api/v1/nlp")
public class NLPController {

    private NLPService nlpService;

    @Autowired
    public NLPController(NLPService nlpService) {
        this.nlpService = nlpService;
    }

    @GetMapping("/search")
    public ResponseEntity<?> nlp(@RequestParam(value = "input") final String content) {
        try {
            return new ResponseEntity<>(nlpService.searchResults(content), HttpStatus.OK);
        } catch (NoProperInput noProperInput) {
            return new ResponseEntity<>("Please provide proper input", HttpStatus.NOT_FOUND);
        } catch (NoSearchResult noSearchResult) {
            return new ResponseEntity<>("No Results found", HttpStatus.NOT_FOUND);
        }
    }

}
