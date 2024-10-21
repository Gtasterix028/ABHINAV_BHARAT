package com.spring.jwt.repository;

import com.spring.jwt.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount,Integer> {

}
