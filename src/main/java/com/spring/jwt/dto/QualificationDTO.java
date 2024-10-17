package com.spring.jwt.dto;

import lombok.Data;

@Data
public class QualificationDTO {

    private Integer QualificationId;
    private String standard;
    private String university;
    private String passingYear;
    private Double percentage;
    private Integer applicationId;

}
