package com.cuahangnongsan.controller.admin;

import com.cuahangnongsan.entity.Product;
import com.cuahangnongsan.entity.User;
import com.cuahangnongsan.exception.StringException;
import com.cuahangnongsan.mapper.CategoryMapper;
import com.cuahangnongsan.mapper.ProductMapper;
import com.cuahangnongsan.dto.request.ProductRequest;
import com.cuahangnongsan.service.ICategoryService;
import com.cuahangnongsan.service.IProductService;
import com.cuahangnongsan.service.IUserService;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.awt.image.ImagingOpException;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/product")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductManageController {

    @Autowired
    IProductService productService;

    @Autowired
    IUserService userService;

    @Autowired
    ICategoryService categoryService;

    @GetMapping("/manage-product")

    public String manageProduct(ModelMap modelMap, String type, String value) {


        List<Product> products = new ArrayList<>();
        try {
            if (type != null && value != null) {
                value = value.trim();
                products = switch (type) {
                    case "name" -> productService.findAllByNameLike("%" + value + "%");
                    case "category" -> productService.findAllByCategoryName("%" + value + "%");
                    case "price" -> productService.findAllByPrice(new BigDecimal(value));
                    case "discount-price" -> productService.findAllByDiscountPrice(new BigDecimal(value));
                    case "unit" -> productService.findAllByUnit(value);
                    case "quantity" -> productService.findAllByQuantity(Integer.parseInt(value));
                    case "remaining-quantity" -> productService.findAllByRemaningQuantity(Integer.parseInt(value));
                    case "description" -> productService.findAllByDescriptionLike("%" + value + "%");
                    case "created-date" -> productService.findAllByCreatedDate(LocalDate.parse(value));
                    case "modified-date" -> productService.findAllByModifiedDate(LocalDate.parse(value));
                    default -> productService.findAll();
                };
            } else {
                products = productService.findAll();
            }
        } catch (Exception e) {
            products = null;
        }
        modelMap.addAttribute("products", products);
        return "admin/manage/manage-product";
    }

    @PostMapping("/manage-product")
    public String redirectActionManageProduct(ModelMap modelMap, String id, String action, RedirectAttributes redirectAttributes) {
        String urlRedirect = "";
        Product product = productService.findById(id);
        if (action.equals("edit")) {
            redirectAttributes.addFlashAttribute("product", product);
            redirectAttributes.addFlashAttribute("categories", categoryService.findAll());
            urlRedirect = "redirect:/admin/product/create-product";
        } else if (action.equals("delete")) {
            productService.delete(product);
            urlRedirect = "redirect:/admin/product/manage-product";
        }
        return urlRedirect;
    }

//    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";
//
//    @PostMapping("/save-product")
//    public String saveProduct(ModelMap modelMap, String id,
//                              MultipartFile file, ProductRequest request, RedirectAttributes redirectAttributes)
//            throws IOException {
//
//        Product oldProduct = null;
//
////        String currentDir = System.getProperty("user.dir");
////
////        Path resourcePath = Paths.get(currentDir, "src", "main", "resources\\static\\img\\products");
//
//        StringBuilder fileNames = new StringBuilder();
//        Category category = categoryService.findById(request.getCategoryId());
//
//        Product product = productMapper.toProduct(request);
//        product.setCreatedDate(LocalDate.now());
//        product.setCategory(category);
//        product.setId(id);
//        ProductResponse responseCreate = productMapper.toProductResponse(product);
//
//        if (file != null) {
//            if (Objects.equals(file.getOriginalFilename(), "") && id == null) {
//                redirectAttributes.addFlashAttribute("errorImage", "Vui lòng chọn ảnh!");
//                redirectAttributes.addFlashAttribute("product",  responseCreate);
//                return "redirect:/admin/product/create-product";
//            } else if (!Objects.equals(file.getOriginalFilename(), "")) {
//                fileNames.append(ProcessImage.upload(file));
//                product.setImage(fileNames.toString());
//                product.setModifiedDate(LocalDate.now());
//            }
//        } else if(id == null) {
//            redirectAttributes.addFlashAttribute("errorImage", "Vui lòng chọn ảnh!");
//            redirectAttributes.addFlashAttribute("product", responseCreate);
//            return "redirect:/admin/product/create-product";
//        }
//
//        if (id != null) {
//            oldProduct = productService.findById(id);
//            product.setImage(!fileNames.toString().isEmpty() ? fileNames.toString() : oldProduct.getImage());
//            product.setId(id);
//            product.setCreatedDate(oldProduct.getCreatedDate());
//        }
//
//        Product checkProduct = productService.findByName(request.getName().trim());
//
//        if (checkProduct != null) {
//            if (id != null && !checkProduct.getId().equals(id)) {
//                redirectAttributes.addFlashAttribute("errorName", "Tên sản phẩm đã tồn tại!");
//                redirectAttributes.addFlashAttribute("product", responseCreate);
//                return "redirect:/admin/product/create-product";
//            } else if (id == null) {
//                redirectAttributes.addFlashAttribute("errorName", "Tên sản phẩm đã tồn tại!");
//                redirectAttributes.addFlashAttribute("product", responseCreate);
//                return "redirect:/admin/product/create-product";
//            }
//        }
//
//        productService.save(product);
//
//        return "redirect:/admin/product/create-product";
//
//    }

    @GetMapping("/create-product")
    public String createProduct(ModelMap model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("errorName", model.getAttribute("errorName"));
        model.addAttribute("errorImage", model.getAttribute("errorImage"));
        return "admin/create/create-product";
    }

    @PostMapping("/save-product")
    public String saveProduct( String id,
                               MultipartFile file, ProductRequest request,
                               RedirectAttributes redirectAttributes) {
        try{
            productService.saveTest(id, file, request, redirectAttributes);
        }catch (IOException | ImagingOpException | StringException ioException){
            return "redirect:/admin/product/create-product";
        }
        return "redirect:/admin/product/create-product";

    }
    @ModelAttribute
    public void commonUser(Principal p, Model m, HttpSession session) {
        if (p != null) {
            String username = p.getName();
            User user = userService.findByUsername(username);
            if (user != null) {
                session.setAttribute("user", user);
                m.addAttribute("user", user);
            }


        }
    }




}
