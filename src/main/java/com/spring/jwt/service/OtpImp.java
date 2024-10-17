package com.spring.jwt.service;

import com.spring.jwt.Interfaces.IOtp;
import com.spring.jwt.entity.OTP;
import com.spring.jwt.repository.OtpRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OtpImp implements IOtp {
    private static final Logger logger = LoggerFactory.getLogger(OtpImp.class);
    @Autowired
    private OtpRepo otpRepo;
    @Override
    public Object save(String number, String otp) {

        OTP otpBuild = OTP.builder()
                        .otp(otp)
                            .mobileNo(number)
                                .dateAndTime(LocalDateTime.now())
                                .build();

            System.out.println(otpBuild);



        otpRepo.save(otpBuild);
        return "otp send";


    }

    @Override
    public Boolean getVerifiedByNumberOtp(String number, String otp) {
        OTP otp1 = otpRepo.findByNumberOtp(number,otp).orElseThrow(()->new RuntimeException("given number and otp invalid"));
        otpRepo.deleteById(otp1.getId());
        return true;

    }
}
