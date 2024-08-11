package com.cuahangnongsan.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

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
