package com.cuahangnongsan.service;

import com.cuahangnongsan.entity.Product;
import com.cuahangnongsan.dto.request.ProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import com.cuahangnongsan.dto.response.*;

public interface IProductService {

    Page<ProductResponse> findAll(Pageable pageable);
    List<ProductResponse> findAll();

    ProductResponse findById(String id);
    ProductResponse findByName(String name);
    Page<ProductResponse> findByNameContainingIgnoreCase(String keyword, Pageable pageable);
    Page<ProductResponse> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection,
                               String category, String priceMin, String priceMax);
    Page<ProductResponse> findPaginatedManage(int pageNo, int pageSize, String type, String value);

    Page<ProductResponse> findAllByCate(Pageable pageable, String id);
    List<ProductResponse> findAllByNameLikeButCurrent(String name, String id);
    List<ProductResponse> findAllByCategoryName(String categoryName);
    void deleteById(String id);
    ProductResponse save(Product product);
    ProductResponse saveTest(String id,
                     MultipartFile file, ProductRequest request, RedirectAttributes redirectAttributes) throws IOException;

    List<ProductResponse> findAllByNameLike(String name);

    int findAllByCreatedDate(LocalDate date);
    int findAllByMonthYear(int month, int year);


}