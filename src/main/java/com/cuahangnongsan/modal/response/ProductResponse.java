package com.cuahangnongsan.modal.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
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

    int quantity;

    int remaningQuantity;

    String image;

    String description;

    LocalDate createDate;

    CategoryResponse category;

    String unit;

}
