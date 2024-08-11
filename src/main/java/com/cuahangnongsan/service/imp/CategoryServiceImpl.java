package com.cuahangnongsan.service.imp;

import com.cuahangnongsan.dto.request.CategoryRequest;
import com.cuahangnongsan.dto.response.CategoryResponse;
import com.cuahangnongsan.entity.Category;
import com.cuahangnongsan.exception.AppException;
import com.cuahangnongsan.mapper.CategoryMapper;
import com.cuahangnongsan.repository.CategoryRepository;
import com.cuahangnongsan.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryResponse> findAllByNameLike(String name) {
        return categoryRepository.findAllByNameLike(name)
                .stream().map(categoryMapper::toCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryResponse> findAllByCodeLike(String code) {
        return categoryRepository.findAllByCodeLike(code)
                .stream().map(categoryMapper::toCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse save(String id, CategoryRequest request, RedirectAttributes redirectAttributes) {

        Category category = categoryMapper.toCategory(request);
        category.setId(id);
        CategoryResponse response = categoryMapper.toCategoryResponse(category);

        if(findByName(request.getName().trim()) != null){
            redirectAttributes.addFlashAttribute("errorName", "Tên đã tồn tại!");
            redirectAttributes.addFlashAttribute("category", response);
            throw new AppException("Name is existed");
        }else if(findByCode(request.getCode().trim()) != null){
            redirectAttributes.addFlashAttribute("errorCode", "Code đã tồn tại!");
            redirectAttributes.addFlashAttribute("category", response);
            throw new AppException("Code is existed");
        }
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryResponse findByIdCategoryResponse(String id) {
        return categoryMapper.toCategoryResponse(categoryRepository.findById(id).orElseThrow());
    }
    @Override
    public Category findByIdCategory(String id) {
        return  (categoryRepository.findById(id).orElseThrow());
    }

    @Override
    public CategoryResponse findByName(String name) {
        return categoryMapper.toCategoryResponse(categoryRepository.findByName(name));
    }

    @Override
    public CategoryResponse findByCode(String code) {
        return categoryMapper.toCategoryResponse(categoryRepository.findByCode(code));
    }

    @Override
    public void deleteById(String id) {
        categoryRepository.deleteById(id);
    }


}
