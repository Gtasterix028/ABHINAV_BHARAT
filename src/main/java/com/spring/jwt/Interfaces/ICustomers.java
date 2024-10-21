package com.spring.jwt.Interfaces;

import com.spring.jwt.dto.CustomersDTO;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ICustomers {
    CustomersDTO getCustomerByID(Integer id);
    

    CustomersDTO saveInformation(CustomersDTO customersDTO);

    CustomersDTO updateAny(Integer id, CustomersDTO customersDTO);

    void deleteCustomer(Integer id);

    List<CustomersDTO> getAllCustomers();
}
