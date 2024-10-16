package com.spring.jwt.controller;

import com.spring.jwt.dto.ApplicationDTO;
import com.spring.jwt.dto.Response;
import com.spring.jwt.entity.Application;
import com.spring.jwt.service.FormUserImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private FormUserImp iFormUser;

//    @GetMapping("/index")
//    public ResponseEntity<String> index(Principal principal){
//        return ResponseEntity.ok("Welcome to admin page : " + principal.getName());
//        
//    }

    @GetMapping("/submitted-today")
    public ResponseEntity<Response> getApplicationsSubmittedToday() {
        try {
            List<Application> applicationDTOList = iFormUser.getAllApplicationsSubmittedToday();
            Response response = new Response("List of Applications submitted today", applicationDTOList, false);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            Response errorResponse = new Response("Failed to retrieve applications submitted today", e.getMessage(), true);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<Response> getAllApplications() {
        try {
            List<ApplicationDTO> applicationDTOList = iFormUser.getAllApplication();
            Response response = new Response("List of the Applicants", applicationDTOList, false);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            Response errorResponse = new Response("Failed to display all Applications", e.getMessage(), true);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}
