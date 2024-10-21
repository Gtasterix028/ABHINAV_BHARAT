package com.spring.jwt.dto;


import lombok.Data;

@Data
public class ProductsDTO {
    private  Integer productID;
    private String productName;
    private String description;
    private Double price;

}
