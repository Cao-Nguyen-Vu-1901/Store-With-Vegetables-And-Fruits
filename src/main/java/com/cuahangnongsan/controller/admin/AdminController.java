package com.cuahangnongsan.controller.admin;


import com.cuahangnongsan.constant.StringConstant;
import com.cuahangnongsan.dto.response.UserResponse;
import com.cuahangnongsan.entity.Invoice;
import com.cuahangnongsan.entity.InvoiceDetail;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.time.LocalDate;
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

    @PostMapping("/calendar")
    public String calculator(String date, ModelMap modelMap, String month, String year) {
        List<Invoice> invoices ;
        int userQuantity = 0, quantitySale = 0,productQuantity = 0 ;

        if(date != null){
            LocalDate localDate = LocalDate.parse(date);
            invoices = invoiceService.findByUpdateDateAndStatus(localDate, StringConstant.STATUS_ORDER_FINISH);
            productQuantity = productService.findAllByCreatedDate(localDate);
            quantitySale = invoiceDetailService.findAllByInvoiceUpdateDate(localDate).size();
            userQuantity = userService.findAllByCreateDateAndRoleName(localDate,StringConstant.ROLE_USER).size();
        }else if( month != null && year != null){
            invoices = invoiceService.findByMonthYearAndStatus( Integer.parseInt(month) + 1, Integer.parseInt(year), StringConstant.STATUS_ORDER_FINISH);
            userQuantity = userService.findAllByMonthYearAndRoleName(Integer.parseInt(month) + 1, Integer.parseInt(year),StringConstant.ROLE_USER).size();
            for (Invoice invoice : invoices)
                for (InvoiceDetail invoiceDetail : invoice.getInvoiceDetails())
                    quantitySale += invoiceDetail.getQuantity();

            productQuantity = productService.findAllByMonthYear(Integer.parseInt(month) + 1, Integer.parseInt(year));
        }else {
            invoices = invoiceService.findAllByStatus(StringConstant.STATUS_ORDER_FINISH);
            userQuantity = userService.findAllByRoleName(StringConstant.ROLE_USER).size();
            quantitySale =  invoiceDetailService.findAll().size();
            productQuantity = productService.findAll().size();
        }
        double revenue = 0;
        for (Invoice item : invoices) {
            for (InvoiceDetail id : item.getInvoiceDetails()) {
                revenue += id.getPrice() * id.getQuantity();
            }
        }

        modelMap.addAttribute("productQuantity", productQuantity);
        modelMap.addAttribute("userQuantity", userQuantity);
        modelMap.addAttribute("quantitySale", quantitySale);
        modelMap.addAttribute("revenue", revenue);

        return "admin/index";
    }

    @GetMapping
    public String home(ModelMap modelMap) {

        List<Invoice> invoices = invoiceService.findAll();
        double revenue = 0;
        for (Invoice item : invoices) {
            if (!item.getStatus().equals(StringConstant.STATUS_ORDER_CANCEL)) {
                for (InvoiceDetail id : item.getInvoiceDetails()) {
                    revenue += id.getPrice() * id.getQuantity();
                }
            }
        }

        modelMap.addAttribute("productQuantity", productService.findAll().size());
        modelMap.addAttribute("userQuantity", userService.findAllByRoleName(StringConstant.ROLE_USER).size());
        modelMap.addAttribute("quantitySale", invoiceDetailService.findAll().size());
        modelMap.addAttribute("revenue", revenue);

        return "admin/index";
    }


    @ModelAttribute
    public void commonUser(Principal p, Model m, HttpSession session) {
        if (p != null) {
            String username = p.getName();
            UserResponse user = userService.findByUsername(username);
            if (user != null) {
                session.setAttribute("user", user);
                m.addAttribute("user", user);
            }


        }
    }
}
