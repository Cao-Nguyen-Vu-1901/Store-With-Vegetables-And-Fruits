package com.cuahangnongsan.repository;

import com.cuahangnongsan.entity.Category;
import com.cuahangnongsan.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    Page<Product> findByNameContainingIgnoreCase(String keyword, Pageable pageable);
    Page<Product> findAllByCategoryId(String id,Pageable pageable);
    Page<Product> findAllByCategoryIdAndName(String id,String name,Pageable pageable);
    Page<Product> findAllByCategoryIdAndDiscountPriceBetween(String category_id, BigDecimal discountPrice, BigDecimal price2, Pageable pageable);
    Page<Product> findAllByNameAndDiscountPriceBetween(String name, BigDecimal discountPrice, BigDecimal discountPrice2, Pageable pageable);
    Page<Product> findAllByCategoryIdAndNameAndDiscountPriceBetween(String category_id,String name, BigDecimal discountPrice, BigDecimal discountPrice2, Pageable pageable);
    Page<Product> findByDiscountPriceBetween(BigDecimal priceMin, BigDecimal priceMax,Pageable pageable);

    Page<Product> findByNameLike(String name, Pageable pageable);

    @Query("select u from Product u where u.name like :name and u.id <> :id")
    List<Product> findAllByNameLikeButId(@Param("name")String name, @Param("id")String id);

    List<Product> findAllByCategoryName(String categoryName);


}
