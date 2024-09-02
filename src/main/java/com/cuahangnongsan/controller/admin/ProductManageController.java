package com.cuahangnongsan.controller.admin;

import com.cuahangnongsan.exception.StringException;
import com.cuahangnongsan.dto.request.ProductRequest;
import com.cuahangnongsan.service.ICategoryService;
import com.cuahangnongsan.service.IProductService;
import com.cuahangnongsan.service.IUserService;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.awt.image.ImagingOpException;
import java.io.IOException;
import java.security.Principal;
import com.cuahangnongsan.dto.response.*;
@Controller
@RequestMapping("/admin/products")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductManageController {

    @Autowired
    IProductService productService;

    @Autowired
    IUserService userService;

    @Autowired
    ICategoryService categoryService;

    @GetMapping("/manage-products")

    public String manageProduct(ModelMap modelMap,
                                @RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "9") int size, String type, String value) {

        Page<ProductResponse> paging = productService.findPaginatedManage(page,size,type,value);
        modelMap.addAttribute("currentPage", paging.getNumber() + 1);
        modelMap.addAttribute("totalItems", paging.getTotalElements());
        modelMap.addAttribute("totalPages", paging.getTotalPages());
        modelMap.addAttribute("pageSize", size);
        modelMap.addAttribute("type", type);
        modelMap.addAttribute("value", value);
        modelMap.addAttribute("products", paging.getContent());
        return "admin/manage/manage-product";
    }

    @PostMapping("/manage-products")
    public String redirectActionManageProduct(ModelMap modelMap, String id, String action, RedirectAttributes redirectAttributes) {
        String urlRedirect = "";
        ProductResponse product = productService.findById(id);
        if (action.equals("edit")) {
            redirectAttributes.addFlashAttribute("product", product);
            redirectAttributes.addFlashAttribute("categories", categoryService.findAll());
            urlRedirect = "redirect:/admin/products/create-products";
        } else if (action.equals("delete")) {
            productService.deleteById(product.getId());
            urlRedirect = "redirect:/admin/products/manage-products";
        }
        return urlRedirect;
    }


    @GetMapping("/create-product")
    public String createProduct(ModelMap model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("errorName", model.getAttribute("errorName"));
        model.addAttribute("errorImage", model.getAttribute("errorImage"));
        return "admin/create/create-product";
    }

    @PostMapping("/save-product")
    public String saveProduct(String id,
                              MultipartFile file, ProductRequest request,
                              RedirectAttributes redirectAttributes) {
        try {
            productService.saveTest(id, file, request, redirectAttributes);
        } catch (IOException | ImagingOpException | StringException ioException) {
            return "redirect:/admin/products/create-product";
        }
        return "redirect:/admin/products/create-product";
    }

    @ModelAttribute
    public void commonUser(Principal p, Model m, HttpSession session) {
        if (p != null) {
            String username = p.getName();
            UserResponse user = userService.findByUsername(username);
            if (user != null) {
                m.addAttribute("user", user);
            }
        }
    }


}
