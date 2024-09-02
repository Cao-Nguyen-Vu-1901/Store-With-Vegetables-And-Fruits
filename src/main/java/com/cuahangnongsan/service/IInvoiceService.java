package com.cuahangnongsan.service;

import com.cuahangnongsan.entity.Invoice;
import com.cuahangnongsan.entity.InvoiceDetail;
import com.cuahangnongsan.entity.User;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import com.cuahangnongsan.dto.response.*;
public interface IInvoiceService {
    void saveInvoiceAndInvoiceDetail(Invoice invoice, List<InvoiceDetail> invoiceDetails );

    Page<InvoiceResponse> invoicePaging( int page, int size ,String type, String value);

    Invoice save(Invoice invoice);
    Invoice findById(String id);
    List<Invoice> findAllByStatusAndUser(String status, User user);
    List<Invoice> findAllByUser(User user);
    List<Invoice> findAll();
    List<Invoice> findByUpdateDateAndStatus(LocalDate date, String status);
    List<Invoice> findByMonthYearAndStatus(int month, int year, String status);
    List<Invoice> findAllByStatus(String status);

    void pay(UserResponse userResponse,List<Cart> selectedProductsCart,String specificAddress, String ward, String district,
             String cityProvince, boolean differentAddress, String  note);

}
