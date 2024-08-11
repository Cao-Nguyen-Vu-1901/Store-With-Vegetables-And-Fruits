package com.cuahangnongsan.mapper;

import com.cuahangnongsan.entity.Category;
import com.cuahangnongsan.modal.request.CategoryRequest;
import com.cuahangnongsan.modal.response.CategoryResponse;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface InvoiceMapper {
    Category toCategory(CategoryRequest request);

    CategoryResponse toCategoryResponse (Category category);
}
