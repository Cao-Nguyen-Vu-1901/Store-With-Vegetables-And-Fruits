package com.cuahangnongsan.controller.admin;


import com.cuahangnongsan.entity.Invoice;
import com.cuahangnongsan.entity.InvoiceDetail;
import com.cuahangnongsan.service.IInvoiceService;
import com.cuahangnongsan.service.IUserService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.cuahangnongsan.dto.response.*;
@Controller
@RequestMapping("/admin/invoices")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvoiceManageController {
    @Autowired
    IInvoiceService invoiceService;

    @Autowired
    IUserService userService;

    ///// Begin show Invoice /////
    @GetMapping({"/show-invoices/{id}" })
    public String showInvoiceDetailWithId(ModelMap modelMap , @PathVariable String id){
        Invoice invoice = invoiceService.findById(id);

        double totalPrice = 0;
        for (InvoiceDetail item : invoice.getInvoiceDetails()){
            totalPrice += item.getPrice() * item.getQuantity();
        }

        modelMap.addAttribute("invoice", invoice);
        modelMap.addAttribute("totalPrice",totalPrice );
        return "admin/show/show-invoice-detail";
    }


    @GetMapping("/show-invoices")
    public String showAllInvoices(ModelMap modelMap,
                                  @RequestParam(defaultValue = "1") int page,
                                  @RequestParam(defaultValue = "9") int size,
                                  String type, String value){
        Page<InvoiceResponse> invoicePage = invoiceService.invoicePaging(page, size, type,value);

        modelMap.addAttribute("invoices", invoicePage.getContent());
        modelMap.addAttribute("currentPage", invoicePage.getNumber() + 1);
        modelMap.addAttribute("totalPages", invoicePage.getTotalPages());
        modelMap.addAttribute("totalItems", invoicePage.getTotalElements());
        modelMap.addAttribute("pageSize", size);
        modelMap.addAttribute("type", type);
        modelMap.addAttribute("value", value);

        return "admin/show/show-invoices";
    }

    ///// End show invoice /////

    ///// Begin manage invoice /////

    @PostMapping("/manage-invoices")
    public String updateInvoice(ModelMap modelMap,  String status, String id){
        Invoice invoice = invoiceService.findById(id);
        if(invoice != null){
            invoice.setStatus(status);
            invoice.setUpdateDate(LocalDate.now());
            invoiceService.save(invoice);
        }
        return "redirect:/admin/invoices/manage-invoices";
    }
    @GetMapping("/manage-invoices")
    public String showInvoicesWithStatus (ModelMap modelMap,  String status){
        modelMap.addAttribute("invoices", status == null ? invoiceService.findAll() : invoiceService.findAllByStatus(status));
        modelMap.addAttribute("status", status);
        return "admin/manage/manage-invoice";
    }

    ///// End manage invoice /////
    @ModelAttribute
    public void commonUser(Principal p, Model m ) {
        if (p != null) {
            String username = p.getName();
            UserResponse user = userService.findByUsername(username);
            if (user != null){
                m.addAttribute("user", user);
            }


        }
    }

    public List<Invoice> searchList(String type, String value){
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
        return invoices;
    }

}
