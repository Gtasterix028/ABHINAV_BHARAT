package com.spring.jwt.service;

import com.spring.jwt.Interfaces.IInvoiceDetails;
import com.spring.jwt.dto.InvoicesDetailsDTO;
import com.spring.jwt.entity.InvoicesDetails;
import com.spring.jwt.repository.InvoiceDetailsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceDetailsServiceImpl implements IInvoiceDetails {

    @Autowired
    private InvoiceDetailsRepository invoicesDetailsRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Object saveInformation(InvoicesDetailsDTO invoicesDetailsDTO) {
        InvoicesDetails invoicesDetails = modelMapper.map(invoicesDetailsDTO, InvoicesDetails.class);
        InvoicesDetails savedInvoicesDetails = invoicesDetailsRepository.save(invoicesDetails);
        return modelMapper.map(savedInvoicesDetails, InvoicesDetailsDTO.class);
    }

    @Override
    public List<InvoicesDetailsDTO> getAllInvoicesDetails() {
        List<InvoicesDetails> invoicesDetailsList = invoicesDetailsRepository.findAll();
        List<InvoicesDetailsDTO> invoicesDetailsDTOList = new ArrayList<>();
        for (InvoicesDetails invoicesDetails : invoicesDetailsList) {
            InvoicesDetailsDTO invoicesDetailsDTO = modelMapper.map(invoicesDetails, InvoicesDetailsDTO.class);
            invoicesDetailsDTOList.add(invoicesDetailsDTO);
        }
        return invoicesDetailsDTOList;
    }

    @Override
    public InvoicesDetailsDTO getById(Integer id) {
        InvoicesDetails invoicesDetails = invoicesDetailsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice detail not found for ID: " + id));
        return modelMapper.map(invoicesDetails, InvoicesDetailsDTO.class);
    }

    @Override
    public InvoicesDetailsDTO updateAny(Integer id, InvoicesDetailsDTO invoicesDetailsDTO) {
        InvoicesDetails invoicesDetails = invoicesDetailsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice detail not found for ID: " + id));

        invoicesDetails.setQuantity(invoicesDetailsDTO.getQuantity());

        InvoicesDetails updatedInvoicesDetails = invoicesDetailsRepository.save(invoicesDetails);
        return modelMapper.map(updatedInvoicesDetails, InvoicesDetailsDTO.class);
    }
}
