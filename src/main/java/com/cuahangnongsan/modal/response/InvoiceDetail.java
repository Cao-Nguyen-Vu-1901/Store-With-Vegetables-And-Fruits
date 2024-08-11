package com.cuahangnongsan.modal.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class InvoiceDetail {

    String id ;

    int quantity;

    double price;

    InvoiceResponse invoice;

    ProductResponse product;


}
