package com.spring.jwt.repository;


import com.spring.jwt.dto.ApplicationDTO;
import com.spring.jwt.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application,Integer> {
    Optional<Application> findByFirstName(String firstName);
    Optional<Application> findByMailID(String mailID);
    Optional<Application> findByAdharCard(String AdharCard);
    Optional<Application> findByPanCardNo(String panCardNo);
    List<Application> findBySubmissionDate(LocalDate submissionDate);
}
