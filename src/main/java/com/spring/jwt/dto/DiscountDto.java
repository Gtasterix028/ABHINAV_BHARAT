package com.spring.jwt.dto;

import lombok.Data;

@Data
public class DiscountDto {
    private  Integer discountId;
    private  String discountName;
    private Double discountValue;

}
