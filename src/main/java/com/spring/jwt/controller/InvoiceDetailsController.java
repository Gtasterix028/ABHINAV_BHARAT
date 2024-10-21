package com.spring.jwt.controller;

import com.spring.jwt.Interfaces.IInvoiceDetails;
import com.spring.jwt.dto.InvoicesDetailsDTO;
import com.spring.jwt.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoiceDetails")
public class InvoiceDetailsController {

        @Autowired
        private IInvoiceDetails iInvoiceDetails; // Service interface for InvoiceDetails

        // Save new invoice detail information
        @PostMapping("/saveInformation")
        public ResponseEntity<Response> saveInformation(@RequestBody InvoicesDetailsDTO invoicesDetailsDTO) {
            try {
                Object savedDTO = iInvoiceDetails.saveInformation(invoicesDetailsDTO); // Save the invoice detail
                return ResponseEntity.ok(new Response("Invoice detail added successfully", savedDTO, false));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new Response("Failed to add invoice detail", e.getMessage(), true));
            }
        }

        // Get all invoice details
        @GetMapping("/getAll")
        public ResponseEntity<Response> getAll() {
            try {
                List<InvoicesDetailsDTO> invoiceDetailsDTOList = iInvoiceDetails.getAllInvoicesDetails(); // Fetch all invoice details
                return ResponseEntity.ok(new Response("List of All Invoice Details", invoiceDetailsDTOList, false));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new Response("Failed to display all invoice details", e.getMessage(), true));
            }
        }

        // Get invoice detail by ID
        @GetMapping("/getById")
        public ResponseEntity<Response> getById(@RequestParam Integer id) {
            try {
                InvoicesDetailsDTO invoiceDetailsDTO = iInvoiceDetails.getById(id); // Fetch invoice detail by ID
                return ResponseEntity.ok(new Response("Invoice detail for ID: " + id, invoiceDetailsDTO, false));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new Response("Failed to retrieve invoice detail for ID: " + id, e.getMessage(), true));
            }
        }

        // Update invoice detail by ID
        @PatchMapping("/updateAny")
        public ResponseEntity<Response> updateAny(@RequestParam Integer id, @RequestBody InvoicesDetailsDTO invoicesDetailsDTO) {
            try {
                InvoicesDetailsDTO updatedDTO = iInvoiceDetails.updateAny(id, invoicesDetailsDTO); // Update the invoice detail
                return ResponseEntity.ok(new Response("Invoice detail updated by ID: " + id, updatedDTO, false));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new Response("Failed to update invoice detail by ID: " + id, e.getMessage(), true));
            }
        }
    }

