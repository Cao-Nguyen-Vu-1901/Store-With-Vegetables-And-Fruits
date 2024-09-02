package com.cuahangnongsan.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {

    String name;

    BigDecimal price;

    BigDecimal discountPrice;

    int quantity;

    int remaningQuantity;

    String description;

    String categoryId;

    String unit;
}
