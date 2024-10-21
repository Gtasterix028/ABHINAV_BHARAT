package com.spring.jwt.Interfaces;

import com.spring.jwt.dto.DiscountDto;
import com.spring.jwt.dto.PaymentMethodDto;

public interface PaymentMethodInterface {
   PaymentMethodDto savePayment(PaymentMethodDto paymentMethodDto);
}
