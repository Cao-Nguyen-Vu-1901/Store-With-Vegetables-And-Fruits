package com.cuahangnongsan.service.imp;

import com.cuahangnongsan.entity.Category;
import com.cuahangnongsan.entity.Product;
import com.cuahangnongsan.exception.StringException;
import com.cuahangnongsan.mapper.ProductMapper;
import com.cuahangnongsan.dto.request.ProductRequest;
import com.cuahangnongsan.dto.response.ProductResponse;
import com.cuahangnongsan.repository.ProductRepository;
import com.cuahangnongsan.service.ICategoryService;
import com.cuahangnongsan.service.IProductService;
import com.cuahangnongsan.util.ProcessImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.awt.image.ImagingOpException;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    ICategoryService categoryService;

    @Override
    public Page<ProductResponse> findAll(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productMapper::toProductResponse);
    }

    @Override
    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream().map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProductResponse> findAllByCate(Pageable pageable, String id) {
        return productRepository.findAllByCategoryId(id, pageable)
                .map(productMapper::toProductResponse);
    }

    @Override
    public List<ProductResponse> findAllByNameLikeButCurrent(String name, String id) {
        return productRepository.findAllByNameLikeButId(name, id)
                .stream().map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> findAllByCategoryName(String categoryName) {
        return productRepository.findAllByCategoryName(categoryName)
                .stream().map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAuthority('AUTHORITY_DELETE_PRODUCT')")
    @Override
    public void deleteById(String id) {
        productRepository.deleteById(id);
    }

    @PreAuthorize("hasAuthority('AUTHORITY_CREATE_PRODUCT')")
    @Override
    public ProductResponse save(Product product) {
        return productMapper.toProductResponse(productRepository.save(product));
    }

    @Override
    public ProductResponse saveTest(String id,
                            MultipartFile file, ProductRequest request, RedirectAttributes redirectAttributes) throws IOException {

        ProductResponse oldProduct = null;

        StringBuilder fileNames = new StringBuilder();
        Category category = categoryService.findByIdCategory(request.getCategoryId());

        Product product = productMapper.toProduct(request);
        product.setCreatedDate(LocalDate.now());
        product.setCategory(category);
        product.setId(id);
        ProductResponse responseCreate = productMapper.toProductResponse(product);

        if (file != null) {
            if (Objects.equals(file.getOriginalFilename(), "") && id == null) {
                redirectAttributes.addFlashAttribute("errorImage", "Vui lòng chọn ảnh!");
                redirectAttributes.addFlashAttribute("product",  responseCreate);
//                return "redirect:/admin/product/create-product";
                throw new ImagingOpException("Image is not found!");
            } else if (!Objects.equals(file.getOriginalFilename(), "")) {
                fileNames.append(ProcessImage.upload(file,"products/"));
                product.setImage(fileNames.toString());
                product.setModifiedDate(LocalDate.now());
            }
        } else if(id == null) {
            redirectAttributes.addFlashAttribute("errorImage", "Vui lòng chọn ảnh!");
            redirectAttributes.addFlashAttribute("product", responseCreate);
//            return "redirect:/admin/product/create-product";
            throw new ImagingOpException("Image is not found!");
        }

        if (id != null) {
            oldProduct = findById(id);
            product.setImage(!fileNames.toString().isEmpty() ? fileNames.toString() : oldProduct.getImage());
            product.setId(id);
            product.setCreatedDate(oldProduct.getCreatedDate());
        }

        ProductResponse checkProduct = findByName(request.getName().trim());

        if (checkProduct != null) {
            if (id != null && !checkProduct.getId().equals(id)) {
                redirectAttributes.addFlashAttribute("errorName", "Tên sản phẩm đã tồn tại!");
                redirectAttributes.addFlashAttribute("product", responseCreate);
                throw new StringException("Name is existed");
            } else if (id == null) {
                redirectAttributes.addFlashAttribute("errorName", "Tên sản phẩm đã tồn tại!");
                redirectAttributes.addFlashAttribute("product", responseCreate);
                throw new StringException("Name is existed");
            }
        }

        return save(product);
    }

    @Override
    public List<ProductResponse> findAllByNameLike(String name) {
        return productRepository.findAllByNameLike(name)
                .stream().map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> findAllByPrice(BigDecimal price) {
        return productRepository.findAllByPrice(price)
                .stream().map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> findAllByDiscountPrice(BigDecimal discount) {
        return productRepository.findAllByDiscountPrice(discount)
                .stream().map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> findAllByUnit(String unit) {
        return productRepository.findAllByUnit(unit)
                .stream().map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> findAllByDescriptionLike(String description) {
        return productRepository.findAllByDescriptionLike(description)
                .stream().map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> findAllByQuantity(int quantity) {
        return productRepository.findAllByQuantity(quantity)
                .stream().map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> findAllByRemaningQuantity(int remaningQuantity) {
        return productRepository.findAllByRemaningQuantity(remaningQuantity)
                .stream().map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> findAllByCreatedDate(LocalDate date) {
        return productRepository.findAllByCreatedDate(date)
                .stream().map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> findAllByModifiedDate(LocalDate date) {
        return productRepository.findAllByModifiedDate(date)
                .stream().map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse findById(String id) {
        return productMapper.toProductResponse(
                productRepository.findById(id).orElseThrow());
    }

    @Override
    public ProductResponse findByName(String name) {
        return productMapper
                .toProductResponse(productRepository.findByName(name));
    }

    @Override
    public Page<ProductResponse> findByNameContainingIgnoreCase(String keyword, Pageable pageable) {
        return productRepository
                .findByNameContainingIgnoreCase(keyword, pageable)
                .map(productMapper::toProductResponse);
    }

    @Override
    public Page<ProductResponse> findPaginated(int pageNo, int pageSize, String name, String condition,
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
            return productRepository
                    .findAllByCategoryIdAndNameAndDiscountPriceBetween(category,
                            name, new BigDecimal(priceMin), new BigDecimal(priceMax), pageable)
                    .map(productMapper::toProductResponse);
        } else if (category != null && !category.isEmpty() && priceMin != null && priceMax != null) {
            return productRepository
                    .findAllByCategoryIdAndDiscountPriceBetween(category,
                            new BigDecimal(priceMin), new BigDecimal(priceMax), pageable)
                    .map(productMapper::toProductResponse);
        } else if (category != null && !category.isEmpty() && name != null) {
            return productRepository.findAllByCategoryIdAndName(category,name, pageable)
                    .map(productMapper::toProductResponse);
        } else if (priceMin != null && priceMax != null && name != null) {
            return productRepository.findAllByNameAndDiscountPriceBetween(name,new BigDecimal(priceMin)
                    , new BigDecimal(priceMax), pageable).map(productMapper::toProductResponse);
        } else if (category != null) {
            return productRepository.findAllByCategoryId(category, pageable)
                    .map(productMapper::toProductResponse);
        } else if (priceMin != null && priceMax != null) {
            return productRepository.findByDiscountPriceBetween( new BigDecimal(priceMin),
                    new BigDecimal(priceMax), pageable)
                    .map(productMapper::toProductResponse);
        }else if (name != null ) {
            return productRepository.findByNameLike("%" + name + "%", pageable)
                    .map(productMapper::toProductResponse)
                    ;
        } else {
            return productRepository.findAll(pageable).map(productMapper::toProductResponse);
        }

    }


}
