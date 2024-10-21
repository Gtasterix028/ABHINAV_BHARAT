package com.spring.jwt.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class InvoicesDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer invoiceDetailID;

    private Integer Quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "InvoiceID", nullable = false)
    private Invoices invoice;

    // Foreign key to Products
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductID", nullable = false)
    private Products product;
}
