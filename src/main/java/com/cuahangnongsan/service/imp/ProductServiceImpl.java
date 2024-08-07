package com.cuahangnongsan.service.imp;

import com.cuahangnongsan.entity.Product;
import com.cuahangnongsan.repository.ProductRepository;
import com.cuahangnongsan.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductRepository productRepository;


    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Page<Product> findAllByCate(Pageable pageable, String id) {
        return productRepository.findAllByCategoryId(id,pageable);
    }

    @Override
    public List<Product> findAllByNameLikeButCurrent(String name, String id) {
        return productRepository.findAllByNameLikeButId(name,id);
    }

    @Override
    public List<Product> findAllByCategoryName(String categoryName) {
        return productRepository.findAllByCategoryName(categoryName);
    }
    @PreAuthorize("hasAuthority('AUTHORITY_DELETE_PRODUCT')")
    @Override
    public void delete(Product product) {
        productRepository.delete(product);
    }

    @PreAuthorize("hasAuthority('AUTHORITY_CREATE_PRODUCT')")
    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> findAllByNameLike(String name) {
        return productRepository.findAllByNameLike(name);
    }

    @Override
    public List<Product> findAllByPrice(BigDecimal price) {
        return productRepository.findAllByPrice(price);
    }

    @Override
    public List<Product> findAllByDiscountPrice(BigDecimal discount) {
        return productRepository.findAllByDiscountPrice(discount);
    }

    @Override
    public List<Product> findAllByUnit(String unit) {
        return productRepository.findAllByUnit(unit);
    }

    @Override
    public List<Product> findAllByDescriptionLike(String description) {
        return productRepository.findAllByDescriptionLike(description);
    }

    @Override
    public List<Product> findAllByQuantity(int quantity) {
        return productRepository.findAllByQuantity(quantity);
    }

    @Override
    public List<Product> findAllByRemaningQuantity(int remaningQuantity) {
        return productRepository.findAllByRemaningQuantity(remaningQuantity);
    }

    @Override
    public List<Product> findAllByCreatedDate(LocalDate date) {
        return productRepository.findAllByCreatedDate(date);
    }

    @Override
    public List<Product> findAllByModifiedDate(LocalDate date) {
        return productRepository.findAllByModifiedDate(date);
    }

    @Override
    public Product findById(String id) {
        return productRepository.findById(id).orElseThrow();
    }

    @Override
    public Page<Product> findByNameContainingIgnoreCase(String keyword, Pageable pageable) {
        return productRepository.findByNameContainingIgnoreCase(keyword, pageable);
    }

    @Override
    public Page<Product> findPaginated(int pageNo, int pageSize, String name, String condition,
                                       String category, String priceMin, String priceMax) {
        Sort sortByCondition = null;Pageable pageable = null;
        if(condition != null){
            if(condition.equals("newest")){
                sortByCondition = Sort.by("createDate").descending();
            }
//            else if (condition.equals("fast-sale")){
//                sortByCondition = condition.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by("price").ascending() :
//                        Sort.by("price").descending();
//            }
            else if (condition.equals("cheapest")) {
                sortByCondition = Sort.by("price").ascending();
            }

            assert sortByCondition != null;
            pageable = PageRequest.of(pageNo - 1, pageSize, sortByCondition);
        }
        else{
            pageable = PageRequest.of(pageNo - 1, pageSize);
        }


        if (category != null && !category.isEmpty() && priceMin != null && priceMax != null && name != null) {
            return productRepository.findAllByCategoryIdAndNameAndDiscountPriceBetween(category , name, new BigDecimal(priceMin), new BigDecimal(priceMax), pageable);
        } else if (category != null && !category.isEmpty() && priceMin != null && priceMax != null) {
            return productRepository.findAllByCategoryIdAndDiscountPriceBetween(category, new BigDecimal(priceMin), new BigDecimal(priceMax), pageable);
        } else if (category != null && !category.isEmpty() && name != null) {
            return productRepository.findAllByCategoryIdAndName(category,name, pageable);
        } else if (priceMin != null && priceMax != null && name != null) {
            return productRepository.findAllByNameAndDiscountPriceBetween(name,new BigDecimal(priceMin), new BigDecimal(priceMax), pageable);
        } else if (category != null) {
            return productRepository.findAllByCategoryId(category, pageable);
        } else if (priceMin != null && priceMax != null) {
            return productRepository.findByDiscountPriceBetween( new BigDecimal(priceMin), new BigDecimal(priceMax), pageable);
        }else if (name != null ) {
            return productRepository.findByNameLike("%"+name+"%",pageable);
        } else {
            return productRepository.findAll(pageable);
        }

    }


}
