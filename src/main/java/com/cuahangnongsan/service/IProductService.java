package com.cuahangnongsan.service;

import com.cuahangnongsan.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface IProductService {

    Page<Product> findAll(Pageable pageable);

    Product findById(String id);
    Page<Product> findByNameContainingIgnoreCase(String keyword, Pageable pageable);
    Page<Product> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection,
                               String category, String priceMin, String priceMax);

    Page<Product> findAllByCate(Pageable pageable, String id);
    List<Product> findAllByNameLikeButCurrent(String name, String id);
    List<Product> findAllByCategoryName(String categoryName);

}