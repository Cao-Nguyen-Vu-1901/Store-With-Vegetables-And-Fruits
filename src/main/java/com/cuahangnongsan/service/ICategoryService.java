package com.cuahangnongsan.service;
import com.cuahangnongsan.dto.response.*;
import com.cuahangnongsan.dto.request.CategoryRequest;
import com.cuahangnongsan.entity.Category;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


public interface ICategoryService {
    List<CategoryResponse> findAll();
    List<CategoryResponse> findAllByNameLike(String name);
    List<CategoryResponse> findAllByCodeLike(String code);

    CategoryResponse save(String id, CategoryRequest request, RedirectAttributes redirectAttributes);

    CategoryResponse findByIdCategoryResponse(String id);
    Category findByIdCategory(String id);
    CategoryResponse findByName(String name);
    CategoryResponse findByCode(String code);

    void deleteById(String id);

}
