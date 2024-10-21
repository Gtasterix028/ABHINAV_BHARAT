package com.spring.jwt.Interfaces;

import com.spring.jwt.dto.InvoicesDetailsDTO;

import java.util.List;

public interface IInvoiceDetails {
    Object saveInformation(InvoicesDetailsDTO invoicesDetailsDTO);

    List<InvoicesDetailsDTO> getAllInvoicesDetails();

    InvoicesDetailsDTO getById(Integer id);

    InvoicesDetailsDTO updateAny(Integer id, InvoicesDetailsDTO invoicesDetailsDTO);
}
