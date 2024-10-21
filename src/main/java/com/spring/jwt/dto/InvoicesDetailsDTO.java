package com.spring.jwt.dto;

import com.spring.jwt.entity.Invoices;
import com.spring.jwt.entity.Products;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class InvoicesDetailsDTO {
    private Integer invoiceDetailID;
    private Integer Quantity;
    private Invoices invoice;

    private Products product;
}
