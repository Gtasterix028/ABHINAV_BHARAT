package com.spring.jwt.repository;

import com.spring.jwt.entity.Application;
import com.spring.jwt.entity.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
public class ApplicationRepositoryTest {

    @Mock
    private ApplicationRepository applicationRepository;

    private Application application;

    @BeforeEach
    void setUp() {
        application = new Application();
        application.setFirstName("John");
        application.setMailID("john.doe@example.com");
        application.setAdharCard("123456789012");
        application.setPanCardNo("ABCDE1234F");
        application.setSubmissionDate(LocalDate.of(2023, 1, 1));
        application.setDate(LocalDate.of(2023, 1, 2));
        application.setPaymentStatus(PaymentStatus.NO_PAYMENT);
    }

    @Test
    void testFindByFirstName() {
        when(applicationRepository.findByFirstName("John")).thenReturn(Optional.of(application));

        Optional<Application> result = applicationRepository.findByFirstName("John");

        assertTrue(result.isPresent());
        assertEquals("John", result.get().getFirstName());
    }

    @Test
    void testFindByMailID() {
        when(applicationRepository.findByMailID("john.doe@example.com")).thenReturn(Optional.of(application));

        Optional<Application> result = applicationRepository.findByMailID("john.doe@example.com");

        assertTrue(result.isPresent());
        assertEquals("john.doe@example.com", result.get().getMailID());
    }

    @Test
    void testFindByAdharCard() {
        when(applicationRepository.findByAdharCard("123456789012")).thenReturn(Optional.of(application));

        Optional<Application> result = applicationRepository.findByAdharCard("123456789012");

        assertTrue(result.isPresent());
        assertEquals("123456789012", result.get().getAdharCard());
    }

    @Test
    void testFindByPanCardNo() {
        when(applicationRepository.findByPanCardNo("ABCDE1234F")).thenReturn(Optional.of(application));

        Optional<Application> result = applicationRepository.findByPanCardNo("ABCDE1234F");

        assertTrue(result.isPresent());
        assertEquals("ABCDE1234F", result.get().getPanCardNo());
    }

    @Test
    void testFindBySubmissionDate() {
        List<Application> applications = Arrays.asList(application);
        when(applicationRepository.findBySubmissionDate(LocalDate.of(2023, 1, 1))).thenReturn(applications);

        List<Application> result = applicationRepository.findBySubmissionDate(LocalDate.of(2023, 1, 1));

        assertFalse(result.isEmpty());
        assertEquals(LocalDate.of(2023, 1, 1), result.get(0).getSubmissionDate());
    }

    @Test
    void testFindByDate() {
        List<Application> applications = Arrays.asList(application);
        when(applicationRepository.findByDate(LocalDate.of(2023, 1, 2))).thenReturn(applications);

        List<Application> result = applicationRepository.findByDate(LocalDate.of(2023, 1, 2));

        assertFalse(result.isEmpty());
        assertEquals(LocalDate.of(2023, 1, 2), result.get(0).getDate());
    }

    @Test
    void testFindByPaymentStatus() {
        List<Application> applications = Arrays.asList(application);
        when(applicationRepository.findByPaymentStatus(PaymentStatus.PENDING)).thenReturn(applications);

        List<Application> result = applicationRepository.findByPaymentStatus(PaymentStatus.PENDING);

        assertFalse(result.isEmpty());
        assertEquals(PaymentStatus.PENDING, result.get(0).getPaymentStatus());
    }
}
