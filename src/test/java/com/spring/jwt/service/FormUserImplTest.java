package com.spring.jwt.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import com.spring.jwt.Interfaces.IFormUser ;
import com.spring.jwt.Mapper.ApplicationMapper;
import com.spring.jwt.dto.ApplicationDTO;
import com.spring.jwt.entity.Application;
import com.spring.jwt.entity.PaymentStatus;
import com.spring.jwt.repository.ApplicationRepository;
import com.spring.jwt.repository.QualificationRepository;
import com.spring.jwt.repository.AddressRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ContextConfiguration(classes = {FormUserImpl.class})
@ExtendWith(SpringExtension.class)
class FormUserImplTest {

    @Autowired
    private FormUserImpl formUserImpl;

    @MockBean
    private ApplicationRepository applicationRepository;

    @MockBean
    private QualificationRepository qualificationRepository;

    @MockBean
    private AddressRepository addressRepository;

    @MockBean
    private MultipartFile mockFile;

    /**
     * Method under test: {@link FormUserImpl#saveSvayamSavikaForm(ApplicationDTO, MultipartFile)}
     */
    @Test
    void testSaveSvayamSavikaForm() throws IOException {
        // Arrange
        ApplicationDTO applicationDTO = new ApplicationDTO();
        applicationDTO.setMailID("test@example.com");
        applicationDTO.setAdharCard("123456789012");
        applicationDTO.setPanCardNo("ABCDE1234F");
        applicationDTO.setMobileNo("9876543210");

        Application application = ApplicationMapper.toApplicationEntity(applicationDTO);
        when(applicationRepository.findByMailID(applicationDTO.getMailID())).thenReturn(Optional.empty());
        when(applicationRepository.findByAdharCard(applicationDTO.getAdharCard())).thenReturn(Optional.empty());
        when(applicationRepository.findByPanCardNo(applicationDTO.getPanCardNo())).thenReturn(Optional.empty());
        when(mockFile.getBytes()).thenReturn("fileContent".getBytes());
        when(mockFile.getOriginalFilename()).thenReturn("resume.pdf");
        when(mockFile.getContentType()).thenReturn("application/pdf");
        when(applicationRepository.save(Mockito.any(Application.class))).thenReturn(application);

        // Act
        Object result = formUserImpl.saveSvayamSavikaForm(applicationDTO, mockFile);

        // Assert
        verify(applicationRepository).save(Mockito.any(Application.class));
        // You can add more assertions based on the expected outcome
    }

    /**
     * Method under test: {@link FormUserImpl#saveSvayamSavikaForm(ApplicationDTO, MultipartFile)}
     */
    @Test
    void testSaveSvayamSavikaForm_EmailAlreadyExists() {
        // Arrange
        ApplicationDTO applicationDTO = new ApplicationDTO();
        applicationDTO.setMailID("test@example.com");

        when(applicationRepository.findByMailID(applicationDTO.getMailID())).thenReturn(Optional.of(new Application()));

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> {
            formUserImpl.saveSvayamSavikaForm(applicationDTO, mockFile);
        });
    }

    /**
     * Method under test: {@link FormUserImpl#getAllApplicationsSubmittedToday()}
     */
    @Test
    void testGetAllApplicationsSubmittedToday() {
        // Arrange
        when(applicationRepository.findBySubmissionDate(LocalDate.now())).thenReturn(new ArrayList<>());

        // Act
        List<Application> applications = formUserImpl.getAllApplicationsSubmittedToday();

        // Assert
        verify(applicationRepository).findBySubmissionDate(LocalDate.now());
        assertTrue(applications.isEmpty());
    }

    /**
     * Method under test: {@link FormUserImpl#getAllApplication()}
     */
    @Test
    void testGetAllApplication() {
        // Arrange
        when(applicationRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<ApplicationDTO> actualApplications = formUserImpl.getAllApplication();

        // Assert
        verify(applicationRepository).findAll();
        assertTrue(actualApplications.isEmpty());
    }

    /**
     * Method under test: {@link FormUserImpl#saveImage(Integer, MultipartFile)}
     */
    @Test
    void testSaveImage() throws IOException {
        // Arrange
        Application application = new Application();
        application.setApplicationId(1);
        when(applicationRepository.findById(1)).thenReturn(Optional.of(application ));
        when(mockFile.getBytes()).thenReturn("imageContent".getBytes());
        when(applicationRepository.save(Mockito.any(Application.class))).thenReturn(application);

        // Act
        Object result = formUserImpl.saveImage(1, mockFile);

        // Assert
        verify(applicationRepository).save(Mockito.any(Application.class));
        // You can add more assertions based on the expected outcome
    }

    /**
     * Method under test: {@link FormUserImpl#saveImage(Integer, MultipartFile)}
     */
    @Test
    void testSaveImage_ApplicationNotFound() {
        // Arrange
        when(applicationRepository.findById(1)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(RuntimeException.class, () -> {
            formUserImpl.saveImage(1, mockFile);
        });
    }

    /**
     * Method under test: {@link FormUserImpl#getByDate(LocalDate)}
     */
    @Test
    void testGetByDate() {
        // Arrange
        when(applicationRepository.findByDate(LocalDate.now())).thenReturn(new ArrayList<>());

        // Act
        List<Application> applications = formUserImpl.getByDate(LocalDate.now());

        // Assert
        verify(applicationRepository).findByDate(LocalDate.now());
        assertTrue(applications.isEmpty());
    }

    /**
     * Method under test: {@link FormUserImpl#getPendingApplications()}
     */
    @Test
    void testGetPendingApplications() {
        // Arrange
        when(applicationRepository.findByPaymentStatus(PaymentStatus.PENDING)).thenReturn(new ArrayList<>());

        // Act
        List<Application> applications = formUserImpl.getPendingApplications();

        // Assert
        verify(applicationRepository).findByPaymentStatus(PaymentStatus.PENDING);
        assertTrue(applications.isEmpty());
    }
}