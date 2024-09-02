package com.cuahangnongsan.mapper;

import com.cuahangnongsan.dto.request.InvoiceRequest;
import com.cuahangnongsan.entity.Invoice;
import org.mapstruct.Mapper;
import com.cuahangnongsan.dto.response.*;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {
    Invoice toInvoice(InvoiceRequest request);

    @Mapping(target = "invoiceDetails", ignore = true)
    InvoiceResponse toInvoiceResponse (Invoice invoice);
}
