package com.spring.jwt.repository;

import com.spring.jwt.entity.Application;
import com.spring.jwt.entity.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import com.spring.jwt.entity.Application;
import com.spring.jwt.entity.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QualificationRepository extends JpaRepository<Qualification, Integer> {


    Optional<Qualification> findByApplicationAndStandardAndUniversityAndPassingYearAndPercentage(Application savedApplication, String standard, String university, String passingYear , Double percentage);
}
