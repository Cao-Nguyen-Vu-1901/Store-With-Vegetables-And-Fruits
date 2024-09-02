package com.cuahangnongsan.service.imp;
import com.cuahangnongsan.entity.Invoice;
import com.cuahangnongsan.entity.InvoiceDetail;
import com.cuahangnongsan.repository.InvoiceDetailRepository;
import com.cuahangnongsan.service.IInvoiceDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import com.cuahangnongsan.dto.response.*;
@Service
public class InvoiceDetailServiceImpl implements IInvoiceDetailService {

    @Autowired
    private InvoiceDetailRepository invoiceDetailRepository;
    @Override
    public void save(InvoiceDetail invoiceDetail) {
        invoiceDetailRepository.save(invoiceDetail);
    }

    @Override
    public List<InvoiceDetail> fundAllByInvoice(Invoice invoice) {
        return invoiceDetailRepository.findAllByInvoice(invoice);
    }

    @Override
    public List<InvoiceDetail> findAll() {
        return invoiceDetailRepository.findAll();
    }

    @Override
    public List<InvoiceDetail> findAllByInvoiceUpdateDate(LocalDate date) {
        return invoiceDetailRepository.findAllByInvoiceUpdateDate(date);
    }


}
