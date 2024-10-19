package com.spring.jwt.repository;

import com.spring.jwt.entity.OTP;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
public class OtpRepoTest {

    @Mock
    private OtpRepo otpRepo;

    private OTP otp;

    @BeforeEach
    void setUp() {
        otp = new OTP();
        otp.setMobileNo("1234567890");
        otp.setOtp("987654");
    }

    @Test
    void testFindByNumber() {
        when(otpRepo.findByNumber("1234567890")).thenReturn(Optional.of(otp));

        Optional<OTP> result = otpRepo.findByNumber("1234567890");

        assertTrue(result.isPresent());
        assertEquals("1234567890", result.get().getMobileNo());
    }

    @Test
    void testFindByNumberOtp() {
        when(otpRepo.findByNumberOtp("1234567890", "987654")).thenReturn(Optional.of(otp));

        Optional<OTP> result = otpRepo.findByNumberOtp("1234567890", "987654");

        assertTrue(result.isPresent());
        assertEquals("1234567890", result.get().getMobileNo());
        assertEquals("987654", result.get().getOtp());
    }
}
