package com.spring.jwt.Interfaces;

import com.spring.jwt.dto.InvoiceDTO;

import java.util.List;

public interface IInvoice {

    Object saveInformation(InvoiceDTO invoiceDTO);

    List<InvoiceDTO> getALlInvoices();

    InvoiceDTO getById(Integer id);

    InvoiceDTO updateAny(Integer id, InvoiceDTO invoiceDTO);
}
