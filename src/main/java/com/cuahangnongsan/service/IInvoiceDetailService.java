package com.cuahangnongsan.service;

import com.cuahangnongsan.entity.Invoice;
import com.cuahangnongsan.entity.InvoiceDetail;

import java.time.LocalDate;
import java.util.List;

public interface IInvoiceDetailService {
    void save(InvoiceDetail invoiceDetail);
    List<InvoiceDetail> fundAllByInvoice(Invoice invoice);
    List<InvoiceDetail> findAll();
    List<InvoiceDetail> findAllByInvoiceUpdateDate(LocalDate date);
}
