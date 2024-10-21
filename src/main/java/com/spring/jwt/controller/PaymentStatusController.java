package com.spring.jwt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.jwt.Interfaces.IPaymentStatus;
import com.spring.jwt.dto.PaymentStatusDTO;
import com.spring.jwt.dto.Response;
import com.spring.jwt.entity.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/payment-status") // URL mapping for PaymentStatus
public class PaymentStatusController {

    @Autowired
    private IPaymentStatus iPaymentStatus;

    @Autowired
    private ObjectMapper objectMapper;

    // POST - Save new payment status information
    @PostMapping("/saveInformation")
    public ResponseEntity<Response> savePaymentStatusInformation(@RequestBody PaymentStatusDTO paymentStatus) {
        try {
            Object savedPaymentStatus = iPaymentStatus.savePaymentStatus(paymentStatus);
            Response response = new Response("Payment Status Added", savedPaymentStatus, false);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Response errorResponse = new Response("Failed to Add Payment Status", e.getMessage(), true);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    // GET - Retrieve payment status by ID
    @GetMapping("/getById")
    public ResponseEntity<Response> getPaymentStatusById(@RequestParam Integer paymentStatusID) {
        try {
            PaymentStatus paymentStatus = iPaymentStatus.getPaymentStatusById(paymentStatusID);
            Response response = new Response("Payment Status Retrieved", paymentStatus, false);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Response errorResponse = new Response("Payment Status Not Found", e.getMessage(), true);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    // GET - Retrieve all payment statuses
    @GetMapping("/getAll")
    public ResponseEntity<Response> getAllPaymentStatuses() {
        try {
            List<PaymentStatus> paymentStatuses = iPaymentStatus.getAllPaymentStatuses();
            Response response = new Response("Payment Statuses Retrieved", paymentStatuses, false);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Response errorResponse = new Response("Failed to Retrieve Payment Statuses", e.getMessage(), true);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // PATCH - Update payment status
    @PatchMapping("/updateAny")
    public ResponseEntity<Response> updatePaymentStatus(@RequestParam Integer paymentStatusID, @RequestBody PaymentStatusDTO paymentStatus) {
        try {
            PaymentStatus updatedPaymentStatus = iPaymentStatus.updatePaymentStatus(paymentStatusID, paymentStatus);
            Response response = new Response("Payment Status Updated", updatedPaymentStatus, false);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Response errorResponse = new Response("Failed to Update Payment Status", e.getMessage(), true);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    // DELETE - Delete payment status by ID
    @DeleteMapping("/deleteById")
    public ResponseEntity<Response> deletePaymentStatusById(@RequestParam Integer paymentStatusID) {
        try {
            iPaymentStatus.deletePaymentStatusById(paymentStatusID);
            Response response = new Response("Payment Status Deleted Successfully", null, false);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Response errorResponse = new Response("Failed to Delete Payment Status", e.getMessage(), true);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}

