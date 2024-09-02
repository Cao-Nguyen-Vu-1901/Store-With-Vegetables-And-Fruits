package com.cuahangnongsan.service.imp;

import com.cuahangnongsan.entity.Invoice;
import com.cuahangnongsan.entity.InvoiceDetail;
import com.cuahangnongsan.entity.User;
import com.cuahangnongsan.repository.InvoiceDetailRepository;
import com.cuahangnongsan.repository.InvoiceRepository;
import com.cuahangnongsan.service.IInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class InvoiceServiceImpl implements IInvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceDetailRepository invoiceDetailRepository;
    @Override
    public void saveInvoiceAndInvoiceDetail(Invoice invoice, List<InvoiceDetail> invoiceDetails) {
        invoiceRepository.save(invoice);
        invoiceDetailRepository.saveAll(invoiceDetails);
    }

    @Override
    public Invoice save(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    @Override
    public Invoice findById(String id) {
        return invoiceRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Invoice> findAllByStatusAndUser(String status, User user) {
        return invoiceRepository.findAllByStatusAndUser(status,user);
    }

    @Override
    public List<Invoice> findAllByUser(User user) {
        return invoiceRepository.findAllByUser(user);
    }

    @Override
    public List<Invoice> findAll() {
        return invoiceRepository.findAll();
    }

    @Override
    public List<Invoice> findAllByOrderDate(LocalDate date) {
        return invoiceRepository.findAllByOrderDate(date);
    }

    @Override
    public List<Invoice> findAllByStatus(String status) {
        return invoiceRepository.findAllByStatus(status);
    }

    @Override
    public List<Invoice> findAllByAddress(String address) {
        return invoiceRepository.findAllByAddressLike(address);
    }

    @Override
    public List<Invoice> findAllByCancelDate(LocalDate date) {
        return invoiceRepository.findAllByCancelDate(date);
    }

    @Override
    public List<Invoice> findAllByUserName(String name) {
        return invoiceRepository.findAllByUserNameLike(name);
    }

    @Override
    public List<Invoice> findAllByPhoneNumber(String phoneNumber) {
        return invoiceRepository.findAllByPhoneNumberLike(phoneNumber);
    }

}
