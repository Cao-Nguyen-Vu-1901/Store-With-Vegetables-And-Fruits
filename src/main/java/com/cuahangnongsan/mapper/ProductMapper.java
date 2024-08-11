package com.cuahangnongsan.mapper;

import com.cuahangnongsan.entity.Product;
import com.cuahangnongsan.modal.request.ProductRequest;
import com.cuahangnongsan.modal.response.ProductResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toProduct(ProductRequest request);

    ProductResponse toProductResponse(Product product);

}
