package com.cuahangnongsan.service.imp;

import com.cuahangnongsan.entity.Invoice;
import com.cuahangnongsan.entity.InvoiceDetail;
import com.cuahangnongsan.entity.User;
import com.cuahangnongsan.mapper.InvoiceMapper;
import com.cuahangnongsan.repository.InvoiceDetailRepository;
import com.cuahangnongsan.repository.InvoiceRepository;
import com.cuahangnongsan.service.IInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import com.cuahangnongsan.dto.response.*;
@Service
public class InvoiceServiceImpl implements IInvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceDetailRepository invoiceDetailRepository;

    @Autowired
    private InvoiceMapper invoiceMapper;
    @Override
    public void saveInvoiceAndInvoiceDetail(Invoice invoice, List<InvoiceDetail> invoiceDetails) {
        invoiceRepository.save(invoice);
        invoiceDetailRepository.saveAll(invoiceDetails);
    }

    @Override
    public Page<InvoiceResponse> invoicePaging(int page, int size, String type, String value) {

        Page<InvoiceResponse> invoicePaging = null;
        Pageable pageable = PageRequest.of(page - 1, size);

        try{
            invoicePaging = switch (type){
                case "order-date" -> invoiceRepository
                        .findAllByOrderDateBefore(LocalDate.parse(value), pageable).map(invoiceMapper::toInvoiceResponse);
                case "status" -> invoiceRepository.findAllByStatus(value, pageable).map(invoiceMapper::toInvoiceResponse);
                case "cancel-date" -> invoiceRepository
                        .findAllByUpdateDateBefore(LocalDate.parse(value), pageable)
                        .map(invoiceMapper::toInvoiceResponse);
                case "address" -> invoiceRepository
                        .findAllByAddressLike("%" + value + "%", pageable)
                        .map(invoiceMapper::toInvoiceResponse);
                case "name" -> invoiceRepository
                        .findAllByUserNameLike("%" + value + "%", pageable)
                        .map(invoiceMapper::toInvoiceResponse);
                case "phone" -> invoiceRepository
                        .findAllByUserPhoneNumberLike("%" + value + "%", pageable)
                        .map(invoiceMapper::toInvoiceResponse);
                default -> invoiceRepository.findAll(pageable).map(invoiceMapper::toInvoiceResponse);
            };
        }catch (Exception e){
            return invoiceRepository.findAll(pageable).map(invoiceMapper::toInvoiceResponse);
        }

        return invoicePaging;
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
    public List<Invoice> findByUpdateDateAndStatus(LocalDate date, String status) {
        return invoiceRepository.findByUpdateDateAndStatus(date,status);
    }


    @Override
    public List<Invoice> findByMonthYearAndStatus(int month, int year, String status) {
        return invoiceRepository.findByMonthYearAndStatus(month, year, status);
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
        return invoiceRepository.findAllByUpdateDate(date);
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
