package com.cuahangnongsan.modal.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class InvoiceDetail  implements Serializable {

    String id ;

    int quantity;

    double price;

    Invoice invoice;

    Product product;


}
