package com.spring.jwt.repository;

import com.spring.jwt.entity.InvoicesDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InvoiceDetailsRepository extends JpaRepository<InvoicesDetails,Integer> {
}
