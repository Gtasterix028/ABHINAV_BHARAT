package com.spring.jwt.controller;

import static org.mockito.Mockito.when;

import com.spring.jwt.Interfaces.IFormUser;
import com.spring.jwt.entity.Application;
import com.spring.jwt.entity.PaymentStatus;
import com.spring.jwt.repository.ApplicationRepository;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {AdminController.class})
@ExtendWith(SpringExtension.class)
class AdminControllerDiffblueTest {
    @Autowired
    private AdminController adminController;

    @MockBean
    private ApplicationRepository applicationRepository;

    @MockBean
    private IFormUser iFormUser;

    /**
     * Method under test: {@link AdminController#getAllApplications()}
     */
    @Test
    void testGetAllApplications() throws Exception {
        // Arrange
        when(iFormUser.getAllApplication()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/getAll");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"message\":\"List of the Applicants\",\"object\":[],\"hasError\":false}"));
    }

    /**
     * Method under test: {@link AdminController#getByDate(LocalDate)}
     */
    @Test
    void testGetByDate() throws Exception {
        // Arrange
        when(iFormUser.getByDate(Mockito.<LocalDate>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/admin/getByDate");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("date", String.valueOf(LocalDate.of(1970, 1, 1)));

        // Act and Assert
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"message\":\"List of Applications Sort by Date\",\"object\":[],\"hasError\":false}"));
    }

    /**
     * Method under test: {@link AdminController#getByDate(LocalDate)}
     */
    @Test
    void testGetByDate2() throws Exception {
        // Arrange
        when(iFormUser.getByDate(Mockito.<LocalDate>any()))
                .thenThrow(new RuntimeException("List of Applications Sort by Date"));
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/admin/getByDate");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("date", String.valueOf(LocalDate.of(1970, 1, 1)));

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"message\":\"Failed to retrieve applications by Date\",\"object\":\"List of Applications Sort by"
                                + " Date\",\"hasError\":true}"));
    }

    /**
     * Method under test:
     * {@link AdminController#updatePaymentStatus(Integer, Boolean)}
     */
    @Test
    void testUpdatePaymentStatus() throws Exception {
        // Arrange
        Application application = new Application();
        application.setAddresses(new ArrayList<>());
        application.setAdharCard("Adhar Card");
        application.setAlternateNo("Alternate No");
        application.setApplicationId(1);
        application.setApplyFor("Apply For");
        application.setDate(LocalDate.of(1970, 1, 1));
        application.setDob(LocalDate.of(1970, 1, 1));
        application.setExperienceDays("Experience Days");
        application.setExperienceMonths("Experience Months");
        application.setExperienceYear("Experience Year");
        application.setFirstName("Jane");
        application.setGender("Gender");
        application.setImage("AXAXAXAX".getBytes("UTF-8"));
        application.setLastName("Doe");
        application.setMailID("Mail ID");
        application.setMaritalStatus("Marital Status");
        application.setMiddleName("Middle Name");
        application.setMobileNo("Mobile No");
        application.setOrganizationName("Organization Name");
        application.setPanCardNo("Pan Card No");
        application.setPaymentStatus(PaymentStatus.NO_PAYMENT);
        application.setPosition("Position");
        application.setQualifications(new ArrayList<>());
        application.setResume("AXAXAXAX".getBytes("UTF-8"));
        application.setResumeName("Resume Name");
        application.setResumeType("Resume Type");
        application.setSubmissionDate(LocalDate.of(1970, 1, 1));
        application.setTypeOfEngagement("Type Of Engagement");
        application.setWorkingLocation("Working Location");
        when(iFormUser.updatePaymentStatus(Mockito.<Integer>any(), Mockito.<Boolean>any())).thenReturn(application);
        MockHttpServletRequestBuilder putResult = MockMvcRequestBuilders.put("/admin/update-status");
        MockHttpServletRequestBuilder paramResult = putResult.param("adminApproved", String.valueOf(true));
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("id", String.valueOf(1));

        // Act and Assert
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"applicationId\":1,\"applyFor\":\"Apply For\",\"firstName\":\"Jane\",\"middleName\":\"Middle Name\",\"lastName\":"
                                        + "\"Doe\",\"gender\":\"Gender\",\"mailID\":\"Mail ID\",\"mobileNo\":\"Mobile No\",\"alternateNo\":\"Alternate No\",\"dob\""
                                        + ":\"1970-01-01\",\"maritalStatus\":\"Marital Status\",\"adharCard\":\"Adhar Card\",\"panCardNo\":\"Pan Card"
                                        + " No\",\"qualifications\":[],\"addresses\":[],\"organizationName\":\"Organization Name\",\"workingLocation\":\"Working"
                                        + " Location\",\"position\":\"Position\",\"typeOfEngagement\":\"Type Of Engagement\",\"experienceYear\":\"Experience"
                                        + " Year\",\"experienceMonths\":\"Experience Months\",\"experienceDays\":\"Experience Days\",\"submissionDate\":"
                                        + "[1970,1,1],\"date\":[1970,1,1],\"resume\":\"QVhBWEFYQVg=\",\"resumeType\":\"Resume Type\",\"resumeName\":\"Resume"
                                        + " Name\",\"image\":\"QVhBWEFYQVg=\",\"paymentStatus\":\"NO_PAYMENT\"}"));
    }

    /**
     * Method under test: {@link AdminController#getAllApplications()}
     */
    @Test
    void testGetAllApplications2() throws Exception {
        // Arrange
        when(iFormUser.getAllApplication()).thenThrow(new RuntimeException("List of the Applicants"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/getAll");

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"message\":\"Failed to display all Applications\",\"object\":\"List of the Applicants\",\"hasError\":true}"));
    }

    /**
     * Method under test: {@link AdminController#getApplicationsSubmittedToday()}
     */
    @Test
    void testGetApplicationsSubmittedToday() throws Exception {
        // Arrange
        when(iFormUser.getAllApplicationsSubmittedToday()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/submitted-today");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"message\":\"List of Applications submitted today\",\"object\":[],\"hasError\":false}"));
    }

    /**
     * Method under test: {@link AdminController#getApplicationsSubmittedToday()}
     */
    @Test
    void testGetApplicationsSubmittedToday2() throws Exception {
        // Arrange
        when(iFormUser.getAllApplicationsSubmittedToday())
                .thenThrow(new RuntimeException("List of Applications submitted today"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/submitted-today");

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"message\":\"Failed to retrieve applications submitted today\",\"object\":\"List of Applications submitted"
                                        + " today\",\"hasError\":true}"));
    }

    /**
     * Method under test: {@link AdminController#getByPending()}
     */
    @Test
    void testGetByPending() throws Exception {
        // Arrange
        when(iFormUser.getPendingApplications()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/paymentStatus/Pending");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"message\":\"List of the Pending Status\",\"object\":[],\"hasError\":false}"));
    }

    /**
     * Method under test: {@link AdminController#getByPending()}
     */
    @Test
    void testGetByPending2() throws Exception {
        // Arrange
        when(iFormUser.getPendingApplications()).thenThrow(new RuntimeException("List of the Pending Status"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/paymentStatus/Pending");

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"message\":\"Failed to display list of pending\",\"object\":\"List of the Pending Status\",\"hasError"
                                        + "\":true}"));
    }

    /**
     * Method under test: {@link AdminController#getResume(Integer)}
     */
    @Test
    void testGetResume() throws Exception {
        // Arrange
        Application application = new Application();
        application.setAddresses(new ArrayList<>());
        application.setAdharCard("Adhar Card");
        application.setAlternateNo("Alternate No");
        application.setApplicationId(1);
        application.setApplyFor("Apply For");
        application.setDate(LocalDate.of(1970, 1, 1));
        application.setDob(LocalDate.of(1970, 1, 1));
        application.setExperienceDays("Experience Days");
        application.setExperienceMonths("Experience Months");
        application.setExperienceYear("Experience Year");
        application.setFirstName("Jane");
        application.setGender("Gender");
        application.setImage("AXAXAXAX".getBytes("UTF-8"));
        application.setLastName("Doe");
        application.setMailID("Mail ID");
        application.setMaritalStatus("Marital Status");
        application.setMiddleName("Middle Name");
        application.setMobileNo("Mobile No");
        application.setOrganizationName("Organization Name");
        application.setPanCardNo("Pan Card No");
        application.setPaymentStatus(PaymentStatus.NO_PAYMENT);
        application.setPosition("Position");
        application.setQualifications(new ArrayList<>());
        application.setResume("AXAXAXAX".getBytes("UTF-8"));
        application.setResumeName("Resume Name");
        application.setResumeType("Resume Type");
        application.setSubmissionDate(LocalDate.of(1970, 1, 1));
        application.setTypeOfEngagement("Type Of Engagement");
        application.setWorkingLocation("Working Location");
        Optional<Application> ofResult = Optional.of(application);
        when(applicationRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/admin/Resume");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("id", String.valueOf(1));

        // Act and Assert
        MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"message\":\"OK\",\"object\":\"QVhBWEFYQVg=\",\"hasError\":false}"));
    }

    /**
     * Method under test: {@link AdminController#getResume(Integer)}
     */
    @Test
    void testGetResume2() throws Exception {
        // Arrange
        Optional<Application> emptyResult = Optional.empty();
        when(applicationRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/admin/Resume");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("id", String.valueOf(1));

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"message\":\"BAD\",\"object\":\"Application not found\",\"hasError\":true}"));
    }
}
