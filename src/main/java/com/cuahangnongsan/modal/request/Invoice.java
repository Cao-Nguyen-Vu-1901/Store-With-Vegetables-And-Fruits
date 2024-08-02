package com.cuahangnongsan.modal.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Invoice  implements Serializable {


    String id ;

    String status;

    String address;

    List<InvoiceDetail> invoiceDetails = new ArrayList<>();

    User user;

}
