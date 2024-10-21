package com.spring.jwt.repository;

import com.spring.jwt.entity.Invoices;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoices,Integer> {
}
