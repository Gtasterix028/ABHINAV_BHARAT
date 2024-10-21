package com.spring.jwt.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name = "PaymentStatus")
@Data // Generates getters, setters, toString, equals, and hashCode methods
@NoArgsConstructor // Generates a no-argument constructor
@AllArgsConstructor // Generates an all-argument constructor
public class PaymentStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentStatusID; // Wrapper class for Integer

    @Column(name = "PaymentID")
    private Integer paymentID; // Wrapper class for Integer

    @Column(name = "Status")
    private String status; // String for status
}
