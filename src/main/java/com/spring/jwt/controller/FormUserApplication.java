package com.spring.jwt.controller;


import com.spring.jwt.Interfaces.IFormUser;
import com.spring.jwt.dto.ApplicationDTO;
import com.spring.jwt.dto.Response;
import com.spring.jwt.dto.ResponseDto;
import com.spring.jwt.entity.Application;
import com.spring.jwt.service.FormUserImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RequestMapping("/user/form")
@RestController
public class FormUserApplication {

    @Autowired
    private IFormUser iFormUser;

    private FormUserImp formUserImp;


    @PostMapping("/save")
    public ResponseEntity<Response> saveSvayamSavikaForm(@RequestBody ApplicationDTO applicationDTO) {
        try {
            Object savedApplication = iFormUser.saveSvayamSavikaForm(applicationDTO);
            Response response = new Response("Application Added", savedApplication, false);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Response errorResponse = new Response("Failed to Add Application", e.getMessage(), true);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

//    @GetMapping("/submitted-today")
//    public ResponseEntity<Response> getApplicationsSubmittedToday() {
//        try {
//            List<Application> applicationDTOList = iFormUser.getAllApplicationsSubmittedToday();
//            Response response = new Response("List of Applications submitted today", applicationDTOList, false);
//            return ResponseEntity.status(HttpStatus.OK).body(response);
//
//        } catch (Exception e) {
//              Response errorResponse = new Response("Failed to retrieve applications submitted today", e.getMessage(), true);
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
//        }
//    }
//
//    @GetMapping("/getAll")
//    public ResponseEntity<Response> getAllApplications() {
//        try {
//            List<ApplicationDTO> applicationDTOList = iFormUser.getAllApplication();
//            Response response = new Response("List of the Applicants", applicationDTOList, false);
//            return ResponseEntity.status(HttpStatus.OK).body(response);
//        } catch (Exception e) {
//            Response errorResponse = new Response("Failed to display all Applications", e.getMessage(), true);
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
//        }
//    }

}
