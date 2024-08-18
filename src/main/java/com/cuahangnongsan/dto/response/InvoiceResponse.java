package com.cuahangnongsan.dto.response;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvoiceResponse {

    String id ;

    String status;

    String address;

    LocalDate orderDate;

    LocalDate updateDate;

    List<InvoiceDetailResponse> invoiceDetails = new ArrayList<>();

    UserResponse user;

}
