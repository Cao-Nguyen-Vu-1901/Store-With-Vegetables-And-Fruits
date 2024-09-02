package com.cuahangnongsan.service;

import com.cuahangnongsan.entity.Category;

import java.util.List;


public interface ICategoryService {
    List<Category> findAll();

    Category save(Category category);

    Category findById(String id);

    void delete(Category category);

}
