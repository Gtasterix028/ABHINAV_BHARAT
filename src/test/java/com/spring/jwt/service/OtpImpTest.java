package com.spring.jwt.service;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;

import com.spring.jwt.entity.OTP;
import com.spring.jwt.repository.OtpRepo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

@ContextConfiguration(classes = {OtpImp.class})
@ExtendWith(SpringExtension.class)
class OtpImpTest {

    @Autowired
    private OtpImp otpImp;

    @MockBean
    private OtpRepo otpRepo;

    /**
     * Method under test: {@link OtpImp#save(String, String)}
     */
    @Test
    void testSave() {
        // Arrange
        String number = "1234567890";
        String otp = "123456";

        // Act
        String result = (String) otpImp.save(number, otp);

        // Assert
        assertEquals("otp send", result);
        // Verify that the OTP object is saved
        verify(otpRepo).save(Mockito.any(OTP.class));
    }

    /**
     * Method under test: {@link OtpImp#getVerifiedByNumberOtp(String, String)}
     */
    @Test
    void testGetVerifiedByNumberOtp() {
        // Arrange
        String number = "1234567890";
        String otp = "123456";
        OTP otpEntity = OTP.builder()
                .id(1)
                .otp(otp)
                .mobileNo(number)
                .dateAndTime(LocalDateTime.now())
                .build();

        when(otpRepo.findByNumberOtp(number, otp)).thenReturn(Optional.of(otpEntity));

        // Act
        Boolean result = otpImp.getVerifiedByNumberOtp(number, otp);

        // Assert
        assertTrue(result);
        verify(otpRepo).deleteById(otpEntity.getId());
    }

    /**
     * Method under test: {@link OtpImp#getVerifiedByNumberOtp(String, String)}
     */
    @Test
    void testGetVerifiedByNumberOtp_InvalidOtp() {
        // Arrange
        String number = "1234567890";
        String otp = "123456";

        when(otpRepo.findByNumberOtp(number, otp)).thenReturn(Optional.empty());

        // Act and Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            otpImp.getVerifiedByNumberOtp(number, otp);
        });
        assertEquals("given number and otp invalid", thrown.getMessage());
    }
}