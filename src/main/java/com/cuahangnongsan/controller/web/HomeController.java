package com.cuahangnongsan.controller.web;

import com.cuahangnongsan.entity.Post;
import com.cuahangnongsan.service.ICategoryService;
import com.cuahangnongsan.service.IPostService;
import com.cuahangnongsan.service.IProductService;
import com.cuahangnongsan.service.IUserService;
import com.cuahangnongsan.util.ProcessString;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.cuahangnongsan.dto.response.*;
@Controller
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HomeController {

    @Autowired
    IProductService productService;

    @Autowired
    IPostService postService;

    @Autowired
    IUserService userService;

    @Autowired
    ICategoryService categoryService;

    @GetMapping({"/", "/home"})
    public String homePage(ModelMap model) {
        model.addAttribute("pageCurr", "home");
        model.addAttribute("posts", postService.findAll().subList(0,3));
        return "web/index";
    }

    @GetMapping("/product-detail/{id}")
    public String productDetail(ModelMap model, @PathVariable("id") String id) {
        model.addAttribute("pageCurr", "shop");
        ProductResponse product = productService.findById(id);

        List<ProductResponse> listProductsLikeName
                = productService.findAllByNameLikeButCurrent(
                        "%" + ProcessString.getFirstName(product.getName()) +"%"
                      ,product.getId()) ;

        model.addAttribute("product", product);
        model.addAttribute("today", LocalDate.now());
        model.addAttribute("listProductsLikeName",
                listProductsLikeName.subList(0, Math.min(listProductsLikeName.size(), 5)));
        model.addAttribute("listProductsWithCategory",
                productService.findAllByCategoryName(product.getCategory().getName()));


        return "web/product-detail";
    }

    @GetMapping("/shop")
    public String findPaginated(Model model,
                                @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "9") int size,
                                String name, String condition,
                                String category, String priceMin, String priceMax) {
        try {
            List<CategoryResponse> categories = categoryService.findAll();

            List<ProductResponse> tutorials = new ArrayList<ProductResponse>();

            Page<ProductResponse> pageTuts = productService.findPaginated(page, size, name, condition,
                    category, priceMin,
                    priceMax);
            model.addAttribute("name", name);
            model.addAttribute("condition", condition);
            model.addAttribute("category", category);
            model.addAttribute("priceMin", priceMin);
            model.addAttribute("priceMax", priceMax);

            tutorials = pageTuts.getContent();

            model.addAttribute("products", tutorials);
            model.addAttribute("currentPage", pageTuts.getNumber() + 1);
            model.addAttribute("totalItems", pageTuts.getTotalElements());
            model.addAttribute("totalPages", pageTuts.getTotalPages());
            model.addAttribute("pageSize", size);
            model.addAttribute("categories", categories);

        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }

        model.addAttribute("today", LocalDate.now());
        model.addAttribute("pageCurr", "shop");
        return "web/shop";
    }

    @GetMapping("/post-detail/{id}")
    public String showPostDetail(ModelMap modelMap, @PathVariable String id){
        Post post = postService.findById(id);
        modelMap.addAttribute("post", post);
        return "web/post-detail";
    }

    @ModelAttribute
    public void commonUser(Principal p, Model m, HttpSession session) {
        if (p != null) {
            String username = p.getName();
            UserResponse user = userService.findByUsername(username);
            if (user != null){
                 m.addAttribute("user", user);
            }
        }
    }

}
