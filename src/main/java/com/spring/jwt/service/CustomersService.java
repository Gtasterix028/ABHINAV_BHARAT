package com.spring.jwt.service;

import com.spring.jwt.Interfaces.ICustomers;
import com.spring.jwt.config.MapperConfig;
import com.spring.jwt.dto.CustomersDTO;
import com.spring.jwt.entity.Customers;
import com.spring.jwt.repository.CustomersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomersService implements ICustomers {

    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CustomersDTO getCustomerByID(Integer id) {
        Customers customers=customersRepository.findById(id).orElseThrow(()->new RuntimeException("Customer Not Found"));
        return modelMapper.map(customers,CustomersDTO.class);
    }

    @Override
    public CustomersDTO saveInformation(CustomersDTO customersDTO) {
        Customers customer = modelMapper.map(customersDTO, Customers.class);
        Customers savedCustomer = customersRepository.save(customer);
        return modelMapper.map(savedCustomer, CustomersDTO.class);
    }

    @Override
    public CustomersDTO updateAny(Integer id, CustomersDTO customersDTO) {
        Customers customer = customersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + id));


        if (customersDTO.getFirstName() != null) {
            customer.setFirstName(customersDTO.getFirstName());
        }
        if (customersDTO.getLastName() != null) {
            customer.setLastName(customersDTO.getLastName());
        }
        if (customersDTO.getEmail() != null) {
            customer.setEmail(customersDTO.getEmail());
        }
        if (customersDTO.getPhone() != null) {
            customer.setPhone(customersDTO.getPhone());
        }
        if (customersDTO.getAddress() != null) {
            customer.setAddress(customersDTO.getAddress());
        }

        Customers updatedCustomer = customersRepository.save(customer);
        return modelMapper.map(updatedCustomer, CustomersDTO.class);
    }


    @Override
    public void deleteCustomer(Integer id) {
        Customers existingCustomer = customersRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer Not Found"));
        customersRepository.delete(existingCustomer);

    }

    @Override
    public List<CustomersDTO> getAllCustomers() {
            List<Customers> customersList = customersRepository.findAll();
            List<CustomersDTO> customersDTOList = new ArrayList<>();

            for (Customers customer : customersList) {
                CustomersDTO customerDTO = modelMapper.map(customer, CustomersDTO.class);
                customersDTOList.add(customerDTO);
            }

            return customersDTOList;
        }

    }

