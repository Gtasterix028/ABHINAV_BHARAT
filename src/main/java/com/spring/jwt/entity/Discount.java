package com.spring.jwt.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data

public class Discount {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private  Integer discountId;
  private  String discountName;
  private Double discountValue;


}
