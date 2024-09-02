package com.cuahangnongsan.service;

import com.cuahangnongsan.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


public interface IProductService {

    Page<Product> findAll(Pageable pageable);
    List<Product> findAll();

    Product findById(String id);
    Page<Product> findByNameContainingIgnoreCase(String keyword, Pageable pageable);
    Page<Product> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection,
                               String category, String priceMin, String priceMax);

    Page<Product> findAllByCate(Pageable pageable, String id);
    List<Product> findAllByNameLikeButCurrent(String name, String id);
    List<Product> findAllByCategoryName(String categoryName);
    void delete(Product product);
    Product save(Product product);

    List<Product> findAllByNameLike(String name);
    List<Product> findAllByPrice(BigDecimal price);
    List<Product> findAllByDiscountPrice(BigDecimal discount);
    List<Product> findAllByUnit(String unit);
    List<Product> findAllByDescriptionLike(String description);
    List<Product> findAllByQuantity(int quantity);
    List<Product> findAllByRemaningQuantity(int remaningQuantity);
    List<Product> findAllByCreatedDate(LocalDate date);
    List<Product> findAllByModifiedDate(LocalDate date);


}