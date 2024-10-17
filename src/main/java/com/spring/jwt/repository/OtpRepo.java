package com.spring.jwt.repository;

import com.spring.jwt.entity.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepo extends JpaRepository<OTP,Integer> {

    @Query("SELECT o FROM OTP o WHERE o.mobileNo = :mobileNo")
    Optional<OTP> findByNumber(@Param("mobileNo") String mobileNo);
    @Query("SELECT o FROM OTP o WHERE o.mobileNo = :mobileNo AND o.otp = :otp")
    Optional<OTP> findByNumberOtp(@Param("mobileNo") String mobileNo, @Param("otp") String otp);

}
