package com.spring.jwt.controller;


import com.spring.jwt.Interfaces.IFormUser;
import com.spring.jwt.dto.ApplicationDTO;
import com.spring.jwt.dto.Response;
import com.spring.jwt.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user/form")
public class FormUserApplication {
    @Autowired
    private IFormUser iFormUser;

    @PostMapping("/save")
    public ResponseEntity<?> saveSvayamSavikaForm(@RequestBody ApplicationDTO applicationDTO){

        try{
            Object response =iFormUser.saveSvayamSavikaForm(applicationDTO);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Unsuccess",e.getMessage(),true));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Unsuccess",e.getMessage(),true));
        }
    }
}
