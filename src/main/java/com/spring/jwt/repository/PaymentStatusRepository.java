package com.spring.jwt.repository;

import com.spring.jwt.entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentStatusRepository extends JpaRepository<PaymentStatus, Integer> {

//    List<PaymentStatus> findByPaymentId(Integer paymentId);


    // You can define custom query methods here if needed
}
