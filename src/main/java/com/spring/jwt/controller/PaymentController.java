package com.spring.jwt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.jwt.Interfaces.IPayment;
import com.spring.jwt.dto.PaymentDTO;
import com.spring.jwt.dto.Response;
import com.spring.jwt.entity.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/payments") // URL mapping for Payments
public class PaymentController {

    @Autowired
    private IPayment iPayment;

    @Autowired
    private ObjectMapper objectMapper;

    // POST - Save new payment information
    @PostMapping("/saveInformation")
    public ResponseEntity<Response> savePaymentInformation(@RequestBody PaymentDTO payment) {
        try {
            Object savedPayment = iPayment.savePayment(payment);
            Response response = new Response("Payment Added", savedPayment, false);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Response errorResponse = new Response("Failed to Add Payment", e.getMessage(), true);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    // GET - Retrieve payment by ID
    @GetMapping("/getById")
    public ResponseEntity<Response> getPaymentById(@RequestParam Integer paymentID) {
        try {
            Payment payment = iPayment.getPaymentById(paymentID);
            Response response = new Response("Payment Retrieved", payment, false);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Response errorResponse = new Response("Payment Not Found", e.getMessage(), true);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    // GET - Retrieve all payments
    @GetMapping("/getAll")
    public ResponseEntity<Response> getAllPayments() {
        try {
            List<Payment> payments = iPayment.getAllPayments();
            Response response = new Response("Payments Retrieved", payments, false);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Response errorResponse = new Response("Failed to Retrieve Payments", e.getMessage(), true);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // PATCH - Update payment information
    @PatchMapping("/updateAny")
    public ResponseEntity<Response> updatePayment(@RequestParam Integer paymentID, @RequestBody PaymentDTO payment) {
        try {
            Payment updatedPayment = iPayment.updatePayment(paymentID, payment);
            Response response = new Response("Payment Updated", updatedPayment, false);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Response errorResponse = new Response("Failed to Update Payment", e.getMessage(), true);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    // DELETE - Delete payment by ID
    @DeleteMapping("/deleteById")
    public ResponseEntity<Response> deletePaymentById(@RequestParam Integer paymentID) {
        try {
            iPayment.deletePaymentById(paymentID);
            Response response = new Response("Payment Deleted Successfully", null, false);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Response errorResponse = new Response("Failed to Delete Payment", e.getMessage(), true);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }



}
