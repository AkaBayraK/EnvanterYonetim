package com.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exceptions.ApiErrorResponse;
import com.service.ApiExceptionService;

@RestController
@RequestMapping("/api/exceptions")
public class ApiExceptionController {

    private ApiExceptionService apiExceptionService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody ApiErrorResponse response) {
    	//apiExceptionService.save(response);
    	apiExceptionService.save(response);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
    
}