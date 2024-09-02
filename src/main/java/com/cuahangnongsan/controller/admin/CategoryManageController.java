package com.cuahangnongsan.controller.admin;

import com.cuahangnongsan.dto.request.CategoryRequest;
import com.cuahangnongsan.dto.response.CategoryResponse;
import com.cuahangnongsan.dto.response.UserResponse;
import com.cuahangnongsan.mapper.CategoryMapper;
import com.cuahangnongsan.service.ICategoryService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin/categories")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryManageController {

    @Autowired
    ICategoryService categoryService;

    @Autowired
    IUserService userService;


    @GetMapping("/manage-categories")
    public String showViewManage(ModelMap modelMap ,String type, String value){
        List<CategoryResponse> categories =
                type != null && type.equals("code")
                    ? categoryService.findAllByCodeLike("%" + value + "%")
                        : type != null && type.equals("name")
                                ? categoryService.findAllByNameLike("%" + value + "%")
                                : categoryService.findAll();
        modelMap.addAttribute("categories", categories);
        return "admin/manage/manage-category";
    }
    @PostMapping("/manage-categories")
    public String manageCategory(ModelMap modelMap , String id, String action, RedirectAttributes redirectAttributes){
        CategoryResponse category = categoryService.findByIdCategoryResponse(id);
        if(action.equals("edit")){
            redirectAttributes.addFlashAttribute("category", category);
            return "redirect:/admin/categories/create-category";
        }else if(action.equals("delete")){
            categoryService.deleteById(category.getId());
        }
        return "redirect:/admin/categories/manage-categories";
    }
    @GetMapping("/create-category")
    public String showViewCreate(ModelMap modelMap){
        modelMap.addAttribute("category",(CategoryResponse) modelMap.getAttribute("category"));
        return "admin/create/create-category";
    }
    @PostMapping("/save-category")
    public String saveCategory(String id, CategoryRequest request, RedirectAttributes redirectAttributes){
        categoryService.save(id, request, redirectAttributes);
        return "redirect:/admin/categories/create-category";
    }

    @ModelAttribute
    public void commonUser(Principal p, Model m, HttpSession session) {
        if (p != null) {
            String username = p.getName();
            UserResponse user = userService.findByUsername(username);
            if (user != null){
                session.setAttribute("user", user);
                m.addAttribute("user", user);
            }


        }
    }
}
