package com.spring.jwt.repository;

import com.gtasterix.AbhinavNGO.model.Address;
import com.gtasterix.AbhinavNGO.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address,Integer> {
//Optional<Address> findByApplication(Application application, String addressLine1, String city, String state, String zip, String country);


//    Address findByApplication(Application savedApplication, String streetAddress, String district, String pincode, String state, String taluka);


    Optional<Address> findByApplicationAndStreetAddressAndDistrictAndPincodeAndStateAndTaluka(
            Application savedApplication,
            String streetAddress,
            String district,
            String pincode,
            String state,
            String taluka
    );
}