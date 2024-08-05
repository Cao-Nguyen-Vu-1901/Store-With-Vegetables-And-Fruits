package com.cuahangnongsan.controller.admin;


import com.cuahangnongsan.entity.User;
import com.cuahangnongsan.service.IUserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private IUserService userService;

    @GetMapping({"/","/home" })
    public String home(){
        return "admin/index";
    }

    @GetMapping("/create-category")
    public String createCategory(){
        return "admin/create/create-category";
    }
    @GetMapping("/create-post")
    public String createPost(){
        return "admin/create/create-post";
    }
    @GetMapping("/create-user")
    public String createUser(){
        return "admin/create/create-user";
    }
    @GetMapping("/manage-category" )
    public String manageCategory(ModelMap modelMap){

        return "admin/manage/manage-category";
    }
    @GetMapping("/manage-post" )
    public String managePost(){
        return "admin/manage/manage-post";
    }

    @GetMapping("/manage-user")
    public String manageUser(){
        return "admin/manage/manage-user";
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
