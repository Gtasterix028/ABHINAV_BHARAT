package com.spring.jwt.controller;


import com.spring.jwt.Interfaces.PaymentMethodInterface;
import com.spring.jwt.dto.DiscountDto;
import com.spring.jwt.dto.PaymentMethodDto;
import com.spring.jwt.dto.Response;
import com.spring.jwt.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/PaymentMethod")
@RestController

public class PaymentMethodController {
    @Autowired
    private PaymentMethodInterface paymentMethodInterface;
    private PaymentMethodService paymentMethodService;

    @PostMapping("/saveInformation")
    public ResponseEntity<Response> saveDiscount(@RequestBody PaymentMethodDto paymentMethodDto) {
        try {
            PaymentMethodDto savePaymentMethod = paymentMethodInterface.savePayment(paymentMethodDto);
            Response response = new Response("SavedDiscount Added", savePaymentMethod, false);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Response response = new Response("Not Saved Discount", e.getMessage(), true);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        }

    }

}
