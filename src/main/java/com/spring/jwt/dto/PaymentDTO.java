package com.spring.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private Integer paymentID;
    private Integer invoiceID;
    private LocalDate paymentDate;
    private String paymentMethod;
    private Double amount;
}
