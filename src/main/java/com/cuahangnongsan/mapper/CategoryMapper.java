package com.cuahangnongsan.mapper;

import com.cuahangnongsan.entity.Category;
import com.cuahangnongsan.dto.request.CategoryRequest;
import com.cuahangnongsan.entity.Product;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import com.cuahangnongsan.dto.response.*;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryRequest request);


//    @Mapping(target = "products", ignore = true)
    CategoryResponse toCategoryResponse(Category category);


}
