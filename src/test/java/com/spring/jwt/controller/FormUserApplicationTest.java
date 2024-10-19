package com.spring.jwt.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.jwt.Interfaces.IFormUser;
import com.spring.jwt.dto.ApplicationDTO;
import com.spring.jwt.dto.Response;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;


public class FormUserApplicationTest {

    @InjectMocks
    private FormUserApplication formUserApplication;

    @Mock
    private IFormUser iFormUser;

    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveSvayamSavikaForm_Success() throws Exception {
        // Arrange
        String idDto = "{\"firstName\":\"John\", \"lastName\":\"Doe\"}";
        MockMultipartFile file = new MockMultipartFile("file", "resume.pdf", "application/pdf", "resume content".getBytes());

        ApplicationDTO applicationDTO = new ApplicationDTO();
        applicationDTO.setFirstName("John");
        applicationDTO.setLastName("Doe");

        when(objectMapper.readValue(idDto, ApplicationDTO.class)).thenReturn(applicationDTO);
        when(iFormUser.saveSvayamSavikaForm(any(), any())).thenReturn(applicationDTO);

        // Act
        ResponseEntity<Response> responseEntity = formUserApplication.saveSvayamSavikaForm(idDto, file);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("Application Added", responseEntity.getBody().getMessage());
        assertEquals(applicationDTO, responseEntity.getBody().getObject());
        assertEquals(false, responseEntity.getBody().getHasError());
    }

    @Test
    public void testSaveSvayamSavikaForm_Failure() throws Exception {
        // Arrange
        String idDto = "{\"firstName\":\"John\", \"lastName\":\"Doe\"}";
        MockMultipartFile file = new MockMultipartFile("file", "resume.pdf", "application/pdf", "resume content".getBytes());

        when(objectMapper.readValue(idDto, ApplicationDTO.class)).thenThrow(new RuntimeException("Error parsing JSON"));

        // Act
        ResponseEntity<Response> responseEntity = formUserApplication.saveSvayamSavikaForm(idDto, file);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Failed to Add Application", responseEntity.getBody().getMessage());
        assertEquals("Error parsing JSON", responseEntity.getBody().getObject());
        assertEquals(true, responseEntity.getBody().getHasError());
    }

    @Test
    public void testSaveImage_Success() throws Exception {
        // Arrange
        Integer id = 1;
        MockMultipartFile image = new MockMultipartFile("image", "photo.jpg", "image/jpeg", "image content".getBytes());

        when(iFormUser.saveImage(id, image)).thenReturn("Image saved");

        // Act
        ResponseEntity<Response> responseEntity = formUserApplication.saveImage(id, image);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("Image Added Successfully", responseEntity.getBody().getMessage());
        assertEquals("Image saved", responseEntity.getBody().getObject());
        assertEquals(false, responseEntity.getBody().getHasError());
    }

    @Test
    public void testSaveImage_Failure() throws Exception {
        // Arrange
        Integer id = 1;
        MockMultipartFile image = new MockMultipartFile("image", "photo.jpg", "image/jpeg", "image content".getBytes());

        when(iFormUser.saveImage(id, image)).thenThrow(new RuntimeException("Error saving image"));

        // Act
        ResponseEntity<Response> responseEntity = formUserApplication.saveImage(id, image);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Failed To Add Image", responseEntity.getBody().getMessage());
        assertEquals("Error saving image", responseEntity.getBody().getObject());
        assertEquals(true, responseEntity.getBody().getHasError());
    }
}
