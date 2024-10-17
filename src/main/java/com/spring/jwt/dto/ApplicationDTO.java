package com.spring.jwt.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
public class ApplicationDTO {

    private Integer applicationId;
    private String applyFor;
    private String firstName;
    private String middleName;
    private String lastName;
    private String gender;
    private String mailID;
    private String mobileNo ;
    private String alternateNo;
    private LocalDate dob;
    private String maritalStatus;
    private String adharCard;
    private String panCardNo;
    private String organizationName;
    private String workingLocation;
    private String position;
    private String typeOfEngagement;
    private String experienceYear;
    private String experienceMonths;
    private String experienceDays;
    private LocalDate submissionDate;

    private List<QualificationDTO> qualifications;
    private List<AddressDTO> addresses;

    // private MultipartFile resume;
    private byte[] resume;

}
