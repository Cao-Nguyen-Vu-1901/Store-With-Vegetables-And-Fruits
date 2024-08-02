package com.cuahangnongsan.modal.request;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Product implements Serializable {


    String id ;

    String productName;

    BigDecimal price;



    int quantity;


    int remaningQuantity;

    String image;

    String description;


    LocalDate createDate;


    Category category;

    List<InvoiceDetail> invoiceDetail = new ArrayList<>();


}
