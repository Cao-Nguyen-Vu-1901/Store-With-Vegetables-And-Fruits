package com.cuahangnongsan.mapper;

import com.cuahangnongsan.entity.Product;
import com.cuahangnongsan.dto.request.ProductRequest;
import org.mapstruct.Mapper;
import com.cuahangnongsan.dto.response.*;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toProduct(ProductRequest request);

    @Mapping(target = "invoiceDetail", ignore = true)
    @Mapping(target = "comments", ignore = true)
    ProductResponse toProductResponse(Product product);

}
