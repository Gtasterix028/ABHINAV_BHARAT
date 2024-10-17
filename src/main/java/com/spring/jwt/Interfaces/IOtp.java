package com.spring.jwt.Interfaces;

public interface IOtp {
    Object save(String number, String otp);
    Boolean getVerifiedByNumberOtp(String number,String otp);
}
