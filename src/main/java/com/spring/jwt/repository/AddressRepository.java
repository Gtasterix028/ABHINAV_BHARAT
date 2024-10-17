package com.spring.jwt.repository;


import com.spring.jwt.entity.Address;
import com.spring.jwt.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address,Integer> {


    Optional<Address> findByApplicationAndStreetAddressAndDistrictAndPincodeAndStateAndTaluka(
            Application savedApplication,
            String streetAddress,
            String district,
            String pincode,
            String state,
            String taluka
    );
}