package com.spring.jwt.Interfaces;

import com.spring.jwt.dto.PaymentStatusDTO;
import com.spring.jwt.entity.PaymentStatus;
import java.util.List;

public interface IPaymentStatus {
    List<PaymentStatus> getAllPaymentStatuses();

    PaymentStatus getPaymentStatusById(Integer id);

    PaymentStatus savePaymentStatus(PaymentStatusDTO paymentStatusDTO);

    PaymentStatus updatePaymentStatus(Integer id, PaymentStatusDTO paymentStatusDTO);


    void deletePaymentStatusById(Integer paymentStatusID);
}
