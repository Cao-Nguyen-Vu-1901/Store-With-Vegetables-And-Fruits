package com.cuahangnongsan.controller.admin;


import com.cuahangnongsan.constant.StringConstant;
import com.cuahangnongsan.dto.response.UserResponse;
import com.cuahangnongsan.entity.Invoice;
import com.cuahangnongsan.entity.InvoiceDetail;
import com.cuahangnongsan.entity.User;
import com.cuahangnongsan.service.IInvoiceDetailService;
import com.cuahangnongsan.service.IInvoiceService;
import com.cuahangnongsan.service.IProductService;
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
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private IProductService productService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IInvoiceService invoiceService;

    @Autowired
    private IInvoiceDetailService invoiceDetailService;

    @GetMapping({"/",})
    public String home(ModelMap modelMap){
        List<Invoice> invoices = invoiceService.findAll();
        double revenue = 0;
        for (Invoice item : invoices){
            if(!item.getStatus().equals(StringConstant.STATUS_ORDER_CANCEL)){
                for (InvoiceDetail id: item.getInvoiceDetails()){
                    revenue += id.getPrice() * id.getQuantity();
                }
            }
        }

        modelMap.addAttribute("productQuantity", productService.findAll().size());
        modelMap.addAttribute("userQuantity", userService.findAllByRoleName(StringConstant.ROLE_USER).size());
        modelMap.addAttribute("quantitySale", invoiceDetailService.fundAll().size());
        modelMap.addAttribute("revenue", revenue);

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
            UserResponse user = userService.findByUsername(username);
            if (user != null){
                session.setAttribute("user", user);
                m.addAttribute("user", user);
            }


        }
    }
}
