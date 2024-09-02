package com.cuahangnongsan.service;

import com.cuahangnongsan.entity.Invoice;
import com.cuahangnongsan.entity.InvoiceDetail;
import com.cuahangnongsan.entity.User;

import java.time.LocalDate;
import java.util.List;

public interface IInvoiceService {
    void saveInvoiceAndInvoiceDetail(Invoice invoice, List<InvoiceDetail> invoiceDetails );
    Invoice save(Invoice invoice);
    Invoice findById(String id);
    List<Invoice> findAllByStatusAndUser(String status, User user);
    List<Invoice> findAllByUser(User user);
    List<Invoice> findAll();
    List<Invoice> findAllByOrderDate(LocalDate date);
    List<Invoice> findAllByStatus(String status);
    List<Invoice> findAllByAddress(String address);
    List<Invoice> findAllByCancelDate(LocalDate date);
    List<Invoice> findAllByUserName(String name);
    List<Invoice> findAllByPhoneNumber(String phoneNumber);


}
