package com.spring.jwt.entity;



import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Qualification

{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer QualificationId;

    private String standard;
    private String university;
    private String passingYear;
    private Double percentage;

    @ManyToOne
    @JoinColumn(name = "applicationId", nullable = false)
    @JsonBackReference
    private Application application;

}


