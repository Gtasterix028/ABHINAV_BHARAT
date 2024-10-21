package com.spring.jwt.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "Payments")
@Data // Generates getters, setters, toString, equals, and hashCode methods
@NoArgsConstructor // Generates a no-argument constructor
@AllArgsConstructor // Generates an all-argument constructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentID; // Wrapper class for Integer

    private Integer invoiceID;   //for connection ............................RUja Patil

    private LocalDate paymentDate; // Date for payment date

    private String paymentMethod; // String for payment method

    private Double amount; // Wrapper class for Double

    // Optionally, you can add other fields or methods if needed
}
