package com.spring.jwt.repository;

import com.spring.jwt.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
//    List<Payment> findByInvoiceId(Integer invoiceId);


    // You can define custom query methods here if needed
}
