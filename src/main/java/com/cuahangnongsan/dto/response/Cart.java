package com.cuahangnongsan.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cart implements Serializable {

    String productId;

    String id;

    int quantity;

    String productName;

    String image;

    Double price;
}
