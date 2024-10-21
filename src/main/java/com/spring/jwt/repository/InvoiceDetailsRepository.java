package com.spring.jwt.repository;

import com.spring.jwt.entity.InvoicesDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceDetailsRepository extends JpaRepository<InvoicesDetails,Integer> {
}
