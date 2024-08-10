package com.cuahangnongsan.controller.admin;

import com.cuahangnongsan.entity.Category;
import com.cuahangnongsan.entity.User;
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
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/category")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryManageController {

    @Autowired
    ICategoryService categoryService;
    @Autowired
    IUserService userService;
    @GetMapping("/manage-category")
    public String showViewManage(ModelMap modelMap ,String type, String value){
        List<Category> categories =
                type != null && type.equals("code")
                    ? categoryService.findAllByCodeLike("%" + value + "%")
                        : type != null && type.equals("name")
                                ? categoryService.findAllByNameLike("%" + value + "%")
                                : categoryService.findAll();
        modelMap.addAttribute("categories", categories);
        return "admin/manage/manage-category";
    }
    @PostMapping("/manage-category")
    public String manageCategory(ModelMap modelMap , String id, String action, RedirectAttributes redirectAttributes){
        Category category = id != null ? categoryService.findById(id) : null;
        if(action.equals("edit")){
            redirectAttributes.addFlashAttribute("category", category);
            return "redirect:/admin/category/create-category";
        }else if(action.equals("delete")){
            categoryService.delete(category);
        }
        return "redirect:/admin/category/manage-category";
    }
    @GetMapping("/create-category")
    public String showViewCreate(ModelMap modelMap){
        modelMap.addAttribute("category",(Category) modelMap.getAttribute("category"));
        return "admin/create/create-category";
    }
    @PostMapping("/save-category")
    public String saveCategory(ModelMap modelMap, String id, String code, String name , RedirectAttributes redirectAttributes){
        String errorValue = "";
        code = code.trim() ;
        name = name.trim();
        if(categoryService.findByName(name) != null){
            redirectAttributes.addFlashAttribute("errorName", "Tên đã tồn tại!");
            redirectAttributes.addFlashAttribute("category", Category.builder().name(name).code(code).build());
        }else if(categoryService.findByCode(code) != null){
            redirectAttributes.addFlashAttribute("errorCode", "Code đã tồn tại!");
            redirectAttributes.addFlashAttribute("category", Category.builder().name(name).code(code).build());
        }else {
            Category category = Category.builder().id(id).code(code).name(name).build();
            categoryService.save(category);
        }

        return "redirect:/admin/category/create-category";
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
