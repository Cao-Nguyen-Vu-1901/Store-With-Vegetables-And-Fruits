package com.cuahangnongsan.controller.admin;


import com.cuahangnongsan.entity.Invoice;
import com.cuahangnongsan.entity.InvoiceDetail;
import com.cuahangnongsan.entity.User;
import com.cuahangnongsan.service.IInvoiceService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/invoice")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvoiceManageController {
    @Autowired
    IInvoiceService invoiceService;

    @Autowired
    IUserService userService;

    @GetMapping({"/manage-invoice/{id}" })
    public String showInvoice(ModelMap modelMap , @PathVariable String id){
        Invoice invoice = invoiceService.findById(id);

        double totalPrice = 0;
        for (InvoiceDetail item : invoice.getInvoiceDetails()){
            totalPrice = item.getPrice() * item.getQuantity();
        }

        modelMap.addAttribute("invoice", invoice);
        modelMap.addAttribute("totalPrice",totalPrice );
        return "admin/show/show-invoice";
    }


    @GetMapping("/manage-invoice")
    public String manageInvoice(ModelMap modelMap, String type, String value){
        List<Invoice> invoices = new ArrayList<>();

        try {
            if(type != null && value != null){
                value = value.trim();
                invoices = switch (type) {
                    case "order-date" -> invoiceService.findAllByOrderDate(LocalDate.parse(value));
                    case "status" -> invoiceService.findAllByStatus(value);
                    case "cancel-date" -> invoiceService.findAllByCancelDate(LocalDate.parse(value));
                    case "address" -> invoiceService.findAllByAddress("%"+value+"%");
                    case "name" -> invoiceService.findAllByUserName("%"+value+"%");
                    case "phone" -> invoiceService.findAllByPhoneNumber("%"+value+"%");
                    default -> invoiceService.findAll();
                };
            }else{
                invoices = invoiceService.findAll();
            }
        }catch (Exception e){
            invoices = null;
        }
        modelMap.addAttribute("invoices", invoices);
        return "admin/manage/manage-invoice";
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
