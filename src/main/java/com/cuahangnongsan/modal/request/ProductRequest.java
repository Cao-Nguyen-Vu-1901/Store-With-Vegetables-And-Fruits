package com.cuahangnongsan.modal.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
