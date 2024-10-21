package com.spring.jwt.Interfaces;


import com.spring.jwt.dto.PaymentDTO;
import com.spring.jwt.entity.Payment;
import java.util.List;

public interface IPayment {
    List<Payment> getAllPayments();

    Payment getPaymentById(Integer id);

    Payment savePayment(PaymentDTO paymentDTO);

    Payment updatePayment(Integer id, PaymentDTO paymentDTO);


    void deletePaymentById(Integer paymentID);
}
