package com.spring.jwt.dto;

import lombok.Data;

@Data
public class AddressDTO {

    private Integer addressId;
    private String streetAddress;
    private String taluka;
    private String district;
    private String state;
    private String pincode;
    private Integer applicationId;


}
