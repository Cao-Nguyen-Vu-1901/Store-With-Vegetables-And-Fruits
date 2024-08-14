package com.cuahangnongsan.dto.response;

import com.cuahangnongsan.entity.Comment;
import com.cuahangnongsan.entity.InvoiceDetail;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse{

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

    @JsonManagedReference(value = "product_json")
    List<Comment> comments = new ArrayList<>();

    List<InvoiceDetail> invoiceDetail = new ArrayList<>();
}
