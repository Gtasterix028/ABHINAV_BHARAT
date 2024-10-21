package com.spring.jwt.service;


import com.spring.jwt.Interfaces.IPaymentStatus;
import com.spring.jwt.dto.PaymentStatusDTO;
import com.spring.jwt.entity.PaymentStatus;
import com.spring.jwt.repository.PaymentStatusRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentStatusServiceImpl implements IPaymentStatus {

    @Autowired
    private PaymentStatusRepository paymentStatusRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<PaymentStatus> getAllPaymentStatuses() {
        return paymentStatusRepository.findAll();
    }

    @Override
    public PaymentStatus getPaymentStatusById(Integer id) {
        return paymentStatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment Status not found with id: " + id));
    }

    @Override
    public PaymentStatus savePaymentStatus(PaymentStatusDTO paymentStatusDTO) {
        PaymentStatus paymentStatus = modelMapper.map(paymentStatusDTO, PaymentStatus.class);
        return paymentStatusRepository.save(paymentStatus);
    }

    @Override
    public PaymentStatus updatePaymentStatus(Integer id, PaymentStatusDTO paymentStatusDTO) {
        PaymentStatus paymentStatus = getPaymentStatusById(id);
        modelMapper.map(paymentStatusDTO, paymentStatus); // Map values from DTO to existing entity
        return paymentStatusRepository.save(paymentStatus);
    }

    @Override
    public void deletePaymentStatusById(Integer id) {
        PaymentStatus paymentStatus = getPaymentStatusById(id);
        paymentStatusRepository.delete(paymentStatus);
    }

}
