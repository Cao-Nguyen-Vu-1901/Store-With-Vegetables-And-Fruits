package com.cuahangnongsan.modal.response;

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

public class InvoiceResponse  {


    String id ;

    String status;

    String address;

    List<InvoiceDetail> invoiceDetails = new ArrayList<>();

    UserResponse user;

}
