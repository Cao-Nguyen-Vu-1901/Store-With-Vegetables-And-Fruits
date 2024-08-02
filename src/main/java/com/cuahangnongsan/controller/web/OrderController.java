package com.cuahangnongsan.controller.web;

import com.cuahangnongsan.constant.StringConstant;
import com.cuahangnongsan.entity.Invoice;
import com.cuahangnongsan.entity.InvoiceDetail;
import com.cuahangnongsan.entity.Product;
import com.cuahangnongsan.entity.User;
import com.cuahangnongsan.modal.response.Cart;
import com.cuahangnongsan.service.IInvoiceDetailService;
import com.cuahangnongsan.service.IInvoiceService;
import com.cuahangnongsan.service.IProductService;
import com.cuahangnongsan.service.IUserService;
import com.cuahangnongsan.util.ProcessString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private IProductService productService;

    @Autowired
    private IInvoiceService invoiceService;

    @Autowired
    private IInvoiceDetailService invoiceDetailService;

    @Autowired
    private IUserService userService;

    public List<Cart> selectedProductsCart = new ArrayList<>();

    @GetMapping("/cart")
    public String cart(ModelMap model) throws IOException {
        model.addAttribute("currentPage", "");
        return "web/cart";
    }


    @GetMapping("/check-out")
    public String checkout(ModelMap model) {
        if (!selectedProductsCart.isEmpty()) {
            model.addAttribute("currentPage", "");
            model.addAttribute("list", selectedProductsCart);
            return "web/check-out";
        } else {
            return "redirect:/home";
        }
    }

    @PostMapping("/pay")
    public String pay(ModelMap model, String specificAddress, String ward, String district,
                      String cityProvince, boolean differentAddress, String  note) {


        if (!selectedProductsCart.isEmpty()) {
            String address = "";
            User user = (User) model.getAttribute("user");
            if(differentAddress && user != null){
                address = user.getAddress();
            } else {
                address = specificAddress + ", " + ward + ", " + district + ", " + cityProvince;
            }
            Invoice invoice = Invoice.builder()
                    .status("Đang xử lý")
                    .address(address)
                    .user(user)
                    .orderDate(LocalDate.now())
                    .build();

            List<InvoiceDetail> invoiceDetails = new ArrayList<>();
            selectedProductsCart.forEach(a ->
            {
                InvoiceDetail invoiceDetail = InvoiceDetail.builder()
                        .quantity(a.getQuantity())
                        .price(a.getPrice())
                        .product(Product.builder().id(a.getProductId()).build())
                        .invoice(invoice)
                        .build();
                invoiceDetails.add(invoiceDetail);
            });

            invoiceService.saveInvoiceAndInvoiceDetail(invoice, invoiceDetails);
            return "redirect:/user/success-pay";
        } else {
            return "redirect:/home";
        }
    }

    @GetMapping("/success-pay")
    public String successPay(ModelMap model) {
        if (!selectedProductsCart.isEmpty()) {
            model.addAttribute("currentPage", "");
            List<Product> sameProducts = new ArrayList<>();
            selectedProductsCart.forEach(a -> {
                List<Product> products = productService.findAllByNameLikeButCurrent("%" + ProcessString.getFirstName(a.getProductName()) + "%", a.getProductId());
                sameProducts.addAll(products);
            });
            sameProducts.forEach(a -> {
                System.out.println(a.getName());
            });
            model.addAttribute("products", sameProducts);
            return "web/success-pay-products";
        } else {
            return "redirect:/home";
        }
    }


    @PostMapping("/api/selected-products")
    public ResponseEntity<Map<String, String>> submitForm(@RequestBody List<Cart> selectedProducts) {
        // Xử lý form ở đây
        selectedProductsCart.clear();
        Map<String, String> response = new HashMap<>();
        selectedProductsCart = selectedProducts;

        response.put("redirectUrl", "check-out");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/orders")
    public String showOrders(String type, ModelMap model){
        User user = (User) model.getAttribute("user");
        List<Invoice> invoices = null;
        if(type == null){
            type = "all";
            invoices = invoiceService.findAllByUser(user);
        }else {
            invoices = invoiceService.findAllByStatusAndUser(type,user);
        }
        model.addAttribute("type", type);
        model.addAttribute("invoices", invoices);
        return "web/order";
    }

    @PostMapping("/cancel-order")
    public String cancelOrder(String invoiceId, ModelMap model){
        Invoice invoice = invoiceService.findById(invoiceId);
        if(invoice != null){
            invoice.setStatus(StringConstant.STATUS_ORDER_CANCEL);
            invoice.setCancelDate(LocalDate.now());
            invoiceService.save(invoice);
        }
        return "redirect:/user/orders";
    }

    @ModelAttribute
    public void commonUser(Principal p, Model m) {
        if (p != null) {
            String username = p.getName();
            User user = userService.findByUsername(username);
            if (user != null)
                m.addAttribute("user", user);
        }
    }

}
