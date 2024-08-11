package com.cuahangnongsan.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvoiceRequest implements Serializable {

    String address;
    List<InvoiceDetail> invoiceDetails = new ArrayList<>();
    UserRequest user;

}
