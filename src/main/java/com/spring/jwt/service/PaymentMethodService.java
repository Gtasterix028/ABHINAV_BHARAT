package com.spring.jwt.service;

import com.spring.jwt.Interfaces.PaymentMethodInterface;
import com.spring.jwt.dto.DiscountDto;
import com.spring.jwt.dto.PaymentMethodDto;
import com.spring.jwt.entity.Discount;
import com.spring.jwt.entity.PaymentMethods;
import com.spring.jwt.repository.DiscountRepository;
import com.spring.jwt.repository.PaymentMethodRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentMethodService implements PaymentMethodInterface {
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public PaymentMethodDto savePayment(PaymentMethodDto paymentMethodDto) {

        PaymentMethods paymentMethods = modelMapper.map(paymentMethodDto, PaymentMethods.class);
        paymentMethodRepository.save(paymentMethods);
        return modelMapper.map(paymentMethods, PaymentMethodDto.class);
    }
}
