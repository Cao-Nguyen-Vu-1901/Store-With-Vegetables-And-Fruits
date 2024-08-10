package com.cuahangnongsan.service;

import com.cuahangnongsan.entity.Category;

import java.util.List;


public interface ICategoryService {
    List<Category> findAll();
    List<Category> findAllByNameLike(String name);
    List<Category> findAllByCodeLike(String code);

    Category save(Category category);

    Category findById(String id);
    Category findByName(String name);
    Category findByCode(String code);

    void delete(Category category);

}
