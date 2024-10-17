package com.spring.jwt.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;



import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
        import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer applicationId;

    @Column(nullable = false)
    private String applyFor;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String middleName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false, unique = true)
    private String mailID;

    @Column(nullable = false)

    private String mobileNo;

    private String alternateNo;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dob;

    @Column(nullable = false)
    private String maritalStatus;

    @Column(nullable = false)
    private String adharCard;

    @Column(nullable = false)
    private String panCardNo;

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Qualification> qualifications;

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Address> addresses;

    private String organizationName;
    private String workingLocation;
    private String position;
    private String typeOfEngagement;
    private String experienceYear;
    private String experienceMonths;
    private String experienceDays;

    @Column(nullable = false)
    private LocalDate submissionDate;

    @PrePersist
    protected void onCreate(){
        submissionDate = LocalDate.now();
    }

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] resume;

    private String resumeType;
    private String resumeName;
    @Lob
    @Column(columnDefinition="LONGBLOB", name="receiptPayment")
    private byte[] image;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;


}