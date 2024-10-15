package com.spring.jwt.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class LoginDto {
    @JsonIgnore
    public String mobileNo;
    public String email;
    public String password;
}
