package com.spring.jwt.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.jwt.Interfaces.IFormUser;
import com.spring.jwt.dto.ApplicationDTO;
import com.spring.jwt.dto.Response;
import com.spring.jwt.dto.ResponseDto;
import com.spring.jwt.entity.Application;
import com.spring.jwt.repository.ApplicationRepository;
import com.spring.jwt.service.FormUserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/user/form")
public class FormUserApplication {
    @Autowired
    private IFormUser iFormUser;

    private FormUserImpl formUserImp;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ApplicationRepository applicationRepository;


    @PostMapping(value = "/save", consumes = "multipart/form-data")
    public ResponseEntity<Response> saveSvayamSavikaForm(
            @RequestPart("IdDTO") String IdDTOString,
            @RequestPart("file") MultipartFile file) {
        try {
            ApplicationDTO applicationDTO = objectMapper.readValue(IdDTOString, ApplicationDTO.class);
            applicationDTO.setResume(file.getBytes());  // Save file as byte array
            Object savedApplication = iFormUser.saveSvayamSavikaForm(applicationDTO, file);
            Response response = new Response("Application Added", savedApplication, false);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Response errorResponse = new Response("Failed to Add Application", e.getMessage(), true);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PostMapping(value = "/saveImage", consumes = "multipart/form-data")
    public ResponseEntity<Response> saveImage(
            @RequestParam("id") Integer id,
            @RequestPart("image") MultipartFile image) {
        try {
            // Call the service method to save the image
            Object savedImage = iFormUser.saveImage(id, image);

            Response response = new Response("Image Added Successfully", savedImage, false);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Response errorResponse = new Response("Failed To Add Image", e.getMessage(), true);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }



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
}
