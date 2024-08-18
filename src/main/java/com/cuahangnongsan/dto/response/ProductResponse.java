package com.cuahangnongsan.dto.response;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {


    String id ;

    String name;

    BigDecimal price;

    BigDecimal discountPrice;

    String unit;

    int quantity;

    int remaningQuantity;

    String image;

    String description;

    LocalDate createdDate;

    LocalDate modifiedDate;

    CategoryResponse category;

    List<InvoiceDetailResponse> invoiceDetail = new ArrayList<>();

    List<CommentResponse> comments  = new ArrayList<>();

}
