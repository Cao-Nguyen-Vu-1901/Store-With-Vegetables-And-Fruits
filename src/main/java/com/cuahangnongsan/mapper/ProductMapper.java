package com.cuahangnongsan.mapper;

import com.cuahangnongsan.entity.Product;
import com.cuahangnongsan.dto.request.ProductRequest;
import com.cuahangnongsan.dto.response.ProductResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toProduct(ProductRequest request);

    ProductResponse toProductResponse(Product product);

}
