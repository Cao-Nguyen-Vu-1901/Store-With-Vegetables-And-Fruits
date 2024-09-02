package com.cuahangnongsan.service.imp;

import com.cuahangnongsan.entity.Category;
import com.cuahangnongsan.repository.CategoryRepository;
import com.cuahangnongsan.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category findById(String id) {
        return categoryRepository.findById(id).orElseThrow();
    }

    @Override
    public void delete(Category category) {
        categoryRepository.delete(category);
    }


}
