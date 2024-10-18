package com.spring.jwt.controller;

import com.spring.jwt.Interfaces.IFormUser;
import com.spring.jwt.dto.ApplicationDTO;
import com.spring.jwt.dto.Response;
import com.spring.jwt.entity.Application;
import com.spring.jwt.entity.PaymentStatus;
import com.spring.jwt.repository.ApplicationRepository;
import com.spring.jwt.service.FormUserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.security.Principal;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private IFormUser iFormUser;

    @GetMapping("/Resume")
    public ResponseEntity<Response> getResume(@RequestParam Integer id){
        try {
            Application application = applicationRepository.findById(id).orElseThrow(() -> new RuntimeException("Application not found"));
            byte[] image = application.getResume();
            Response response = new Response("OK", image, false);
            return ResponseEntity.status(HttpStatus.FOUND).body(response);
        }
        catch (Exception e){
            Response errorResponse = new Response("BAD", e.getMessage(), true);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @GetMapping("/submitted-today")
    public ResponseEntity<Response> getApplicationsSubmittedToday() {
        try {
            List<Application> applicationList = iFormUser.getAllApplicationsSubmittedToday();
            Response response = new Response("List of Applications submitted today", applicationList, false);
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

    @GetMapping("/getByDate")
    public ResponseEntity<Response> getByDate(@RequestParam LocalDate date){
        try{
            List<Application> applicationList = iFormUser.getByDate(date);
            Response response = new Response("List of Applications Sort by Date",applicationList,false);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e){
            Response errorResponse = new Response("Failed to retrieve applications by Date", e.getMessage(), true);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping("/paymentStatus/Pending")
        public ResponseEntity<Response> getByPending(){
        try {
            List<Application> applicationList = iFormUser.getPendingApplications();
            Response response = new Response("List of the Pending Status", applicationList, false);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            Response errorResponse = new Response("Failed to display list of pending", e.getMessage(), true);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

    }

    @PutMapping("/update-status/{id}")
    public ResponseEntity<Application> updatePaymentStatus(@RequestParam Integer id,
                                                           @RequestParam("adminApproved") Boolean adminApproved) {
        Application updatedApplication = iFormUser.updatePaymentStatus(id, adminApproved);
        return ResponseEntity.ok(updatedApplication);
    }





}
