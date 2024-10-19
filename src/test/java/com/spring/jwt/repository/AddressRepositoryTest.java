package com.spring.jwt.repository;

import com.spring.jwt.entity.Address;
import com.spring.jwt.entity.Application;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
public class AddressRepositoryTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressRepositoryTest addressRepositoryTest;

    private Application savedApplication;
    private Address address;

    @BeforeEach
    void setUp() {
        savedApplication = new Application();
        address = new Address();
        address.setStreetAddress("123 Main St");
        address.setDistrict("District1");
        address.setPincode("12345");
        address.setState("State1");
        address.setTaluka("Taluka1");
    }

    @Test
    void testFindByApplicationAndStreetAddressAndDistrictAndPincodeAndStateAndTaluka() {
        when(addressRepository.findByApplicationAndStreetAddressAndDistrictAndPincodeAndStateAndTaluka(
                any(Application.class),
                any(String.class),
                any(String.class),
                any(String.class),
                any(String.class),
                any(String.class)))
                .thenReturn(Optional.of(address));

        Optional<Address> result = addressRepository.findByApplicationAndStreetAddressAndDistrictAndPincodeAndStateAndTaluka(
                savedApplication,
                "123 Main St",
                "District1",
                "12345",
                "State1",
                "Taluka1"
        );

        assertTrue(result.isPresent());

    }
}
