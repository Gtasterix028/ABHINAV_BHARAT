package com.spring.jwt.dto;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;

@Data
public class InvoiceDTO {

    private Integer invoiceId;
    private LocalDate invoiceDate;
    private LocalDate dueDate;
    private Double total;

    private Customers customer;
}
