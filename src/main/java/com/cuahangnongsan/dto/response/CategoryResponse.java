package com.cuahangnongsan.dto.response;

import com.cuahangnongsan.entity.Product;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class CategoryResponse {

    String id ;

    String code;

    String name;

    List<Product> products = new ArrayList<>();

}
