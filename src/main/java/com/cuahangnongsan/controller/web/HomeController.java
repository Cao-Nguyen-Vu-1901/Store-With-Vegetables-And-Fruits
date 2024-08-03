package com.cuahangnongsan.controller.web;

import com.cuahangnongsan.entity.Category;
import com.cuahangnongsan.entity.Product;
import com.cuahangnongsan.entity.User;
import com.cuahangnongsan.service.ICategoryService;
import com.cuahangnongsan.service.IProductService;
import com.cuahangnongsan.service.IUserService;
import com.cuahangnongsan.util.ProcessString;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private IProductService productService;
    @Autowired
    private IUserService userService;

    @Autowired
    private ICategoryService categoryService;

    @GetMapping({"/", "/home"})
    public String homePage(ModelMap model) {
        model.addAttribute("pageCurr", "home");
        return "web/index";
    }

    @GetMapping("/product-detail/{id}")
    public String productDetail(ModelMap model, @PathVariable("id") String id) {
        model.addAttribute("pageCurr", "shop");
        Product product = productService.findById(id);

        List<Product> listProductsLikeName
                = productService.findAllByNameLikeButCurrent(
                        "%" + ProcessString.getFirstName(product.getName()) +"%"
                      ,product.getId());

        if (product != null) {
            model.addAttribute("product", product);
            model.addAttribute("today", LocalDate.now());
            model.addAttribute("listProductsLikeName",
                    listProductsLikeName.subList(0, Math.min(listProductsLikeName.size(), 5)));
            model.addAttribute("listProductsWithCategory",
                    productService.findAllByCategoryName(product.getCategory().getName()));
        }



        return "web/product-detail";
    }

    @GetMapping("/shop")
    public String findPaginated(Model model,
                                @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "9") int size,
                                String name, String condition,
                                String category, String priceMin, String priceMax) {
        try {
            List<Category> categories = categoryService.findAll();

            List<Product> tutorials = new ArrayList<Product>();

            Page<Product> pageTuts = productService.findPaginated(page, size, name, condition,
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

    @GetMapping("/cm")
    public String comment(){
        return "web/comment";
    }

    @ModelAttribute
    public void commonUser(Principal p, Model m, HttpSession session) {
        if (p != null) {
            String username = p.getName();
            User user = userService.findByUsername(username);
            if (user != null){
                session.setAttribute("user", user);
                m.addAttribute("user", user);
            }


        }
    }

}
