package com.cuahangnongsan.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class InvoiceDetail  implements Serializable {



    int quantity;

    double price;

    InvoiceRequest invoice;

    ProductRequest product;


}
