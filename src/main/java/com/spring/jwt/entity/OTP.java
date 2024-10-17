package com.spring.jwt.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "OTP")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OTP {

    @Id
    @Column(name = "OTP_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "user_id_generator")
    @SequenceGenerator(name = "OTP_ID", initialValue = 1000000)
    private Integer id;

    @Column(name = "Mobile_Number", nullable = false)
    private String mobileNo;
    @Column(name = "Otp", nullable = false)
    private String otp;
    @Column(name = "DATE_AND_TIME", nullable = false)
    private LocalDateTime dateAndTime;

}
