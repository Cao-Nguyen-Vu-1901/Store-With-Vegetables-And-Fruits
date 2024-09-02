package com.cuahangnongsan.repository;

import com.cuahangnongsan.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,String> {
    Category findByName(String name);
    Category findByCode(String code);


    List<Category> findAllByNameLike(String name);
    List<Category> findAllByCodeLike(String code);
}
