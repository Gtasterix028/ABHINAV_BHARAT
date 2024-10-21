package com.spring.jwt.controller;

import com.spring.jwt.Interfaces.ICustomers;
import com.spring.jwt.dto.CustomersDTO;
import com.spring.jwt.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomersController {

    @Autowired
    private ICustomers customersInterface;

    @GetMapping("/getByID")
    public ResponseEntity<Response> getCustomerById(@RequestParam Integer id) {
        try {
            CustomersDTO customersDTO = customersInterface.getCustomerByID(id);
            return ResponseEntity.ok(new Response("Customer retrieved successfully", customersDTO, false));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("An error occurred", e.getMessage(), true));
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<Response> getAllCustomers() {
        try {
            List<CustomersDTO> customersList = customersInterface.getAllCustomers();
            return ResponseEntity.ok(new Response("All customers retrieved successfully", customersList, false));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("An error occurred", e.getMessage(), true));
        }
    }

    @PostMapping("/saveInformation")
    public ResponseEntity<Response> createCustomer(@RequestBody CustomersDTO customersDTO) {
        try {
            CustomersDTO createdCustomer = customersInterface.saveInformation(customersDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Customer created successfully", createdCustomer, false));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("An error occurred", e.getMessage(), true));
        }
    }

    @PatchMapping("/updateAny")
    public ResponseEntity<Response> updateCustomer(@RequestParam Integer id, @RequestBody CustomersDTO customersDTO) {
        try {
            CustomersDTO updatedCustomer = customersInterface.updateAny(id, customersDTO);
            return ResponseEntity.ok(new Response("Customer updated successfully", updatedCustomer, false));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("An error occurred", e.getMessage(), true));
        }
    }

    @DeleteMapping("/deleteByID")
    public ResponseEntity<Response> deleteCustomerBYID(@RequestParam Integer id) {
        try {
            customersInterface.deleteCustomer(id);
            return ResponseEntity.ok(new Response("Customer deleted successfully", null, false));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("An error occurred", e.getMessage(), true));
        }
    }
}
