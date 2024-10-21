package com.spring.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentStatusDTO {
    private Integer paymentStatusID;
    private Integer paymentID;
    private String status;
}

