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
import java.time.LocalDate;
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
    Page<Product> findAllByCategoryNameLike(String name, Pageable pageable);

    Page<Product> findAllByPriceLessThan(BigDecimal price, Pageable pageable);
    Page<Product> findAllByDiscountPriceLessThan(BigDecimal discountPrice, Pageable pageable);
    Page<Product> findAllByUnit(String unit, Pageable pageable);
    Page<Product> findAllByQuantityLessThan(int quantity, Pageable pageable);
    Page<Product> findAllByRemaningQuantityLessThan(int remainingQuantity, Pageable pageable);
    Page<Product> findAllByDescriptionLike(String description, Pageable pageable);

    Page<Product> findAllByCreatedDate(LocalDate createdDate, Pageable pageable);
    Page<Product> findAllByModifiedDate(LocalDate modifiedDate, Pageable pageable);
    Page<Product> findAllByNameLike(String name, Pageable pageable);

    @Query("select u from Product u where u.name like :name and u.id <> :id")
    List<Product> findAllByNameLikeButId(@Param("name")String name, @Param("id")String id);

    @Query("select p from Product p where p.category.name like :name")
    List<Product> findAllByCategoryName(@Param("name") String name);

    List<Product> findAllByNameLike(String name);
    List<Product> findAllByPrice(BigDecimal price);
    List<Product> findAllByDiscountPrice(BigDecimal discount);
    List<Product> findAllByUnit(String unit);
    List<Product> findAllByQuantity(int quantity);
    List<Product> findAllByRemaningQuantity(int remaningQuantity);
    List<Product> findAllByDescriptionLike(String description);
    List<Product> findAllByCreatedDate(LocalDate date);
    List<Product> findAllByModifiedDate(LocalDate date);

    @Query("select p from Product p where extract(month from p.createdDate) = :month and" +
            " extract(year from p.createdDate) = :year ")
    List<Product> findAllByMonthYear(@Param("month") int month, @Param("year") int year);

    Product findByName(String name);
}
