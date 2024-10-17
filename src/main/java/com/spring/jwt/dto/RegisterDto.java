package com.spring.jwt.dto;


import com.spring.jwt.entity.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RegisterDto {


    private String name;

    private String email;

    private String mobileNo;
    public String roles;

    private String password;




}
