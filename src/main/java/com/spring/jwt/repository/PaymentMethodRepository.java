package com.spring.jwt.repository;

import com.spring.jwt.entity.PaymentMethods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethods,Integer> {
}
