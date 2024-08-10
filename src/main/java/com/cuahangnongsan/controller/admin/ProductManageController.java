package com.cuahangnongsan.controller.admin;

import com.cuahangnongsan.entity.Category;
import com.cuahangnongsan.entity.Product;
import com.cuahangnongsan.entity.User;
import com.cuahangnongsan.service.ICategoryService;
import com.cuahangnongsan.service.IProductService;
import com.cuahangnongsan.service.IUserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/admin/product")
public class ProductManageController {

    @Autowired
    private IProductService productService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/manage-product")

    public String manageProduct(ModelMap modelMap, String type, String value) {


        List<Product> products = new ArrayList<>();
        try{
            if( type != null && value != null){
                value = value.trim();
                products = switch (type){
                    case "name" -> productService.findAllByNameLike("%"+value+"%");
                    case "category" -> productService.findAllByCategoryName("%"+value+"%");
                    case "price" -> productService.findAllByPrice(new BigDecimal(value));
                    case "discount-price" -> productService.findAllByDiscountPrice(new BigDecimal(value));
                    case "unit" -> productService.findAllByUnit(value);
                    case "quantity" -> productService.findAllByQuantity(Integer.parseInt(value));
                    case "remaining-quantity" -> productService.findAllByRemaningQuantity(Integer.parseInt(value));
                    case "description" -> productService.findAllByDescriptionLike("%"+value+"%");
                    case "created-date" -> productService.findAllByCreatedDate(LocalDate.parse(value));
                    case "modified-date" -> productService.findAllByModifiedDate(LocalDate.parse(value));
                    default -> productService.findAll();
                } ;
            }else{
                products = productService.findAll();
            }
        }catch (Exception e){
            products = null;
        }
        modelMap.addAttribute("products", products);
        return "admin/manage/manage-product";
    }

    @PostMapping("/manage-product")
    public String redirectActionManageProduct(ModelMap modelMap, String id, String action, RedirectAttributes redirectAttributes) {
        String urlRedirect = "";
        Product product = productService.findById(id);
        if (action.equals("edit")) {
            redirectAttributes.addFlashAttribute("product", product);
            redirectAttributes.addFlashAttribute("categories", categoryService.findAll());
            urlRedirect = "redirect:/admin/product/create-product";
        } else if (action.equals("delete")) {
            productService.delete(product);
            urlRedirect = "redirect:/admin/product/manage-product";
        }
        return urlRedirect;
    }

//    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";

    @PostMapping("/save-product")
    public String saveProduct(ModelMap modelMap, String id, String name,
                              String price, String discountPrice, int quantity,
                              int remaningQuantity, String categoryId,
                              String unit, MultipartFile image, String description)
            throws IOException {

        String currentDir = System.getProperty("user.dir");

        // Tạo đường dẫn đến thư mục resource
        Path resourcePath = Paths.get(currentDir, "src", "main", "resources\\static\\img\\products");

        StringBuilder fileNames = new StringBuilder();

        Category category = categoryService.findById(categoryId);
        if (!Objects.equals(image.getOriginalFilename(), "")) {
            String imgName = UUID.randomUUID()+image.getOriginalFilename();
            Path fileNameAndPath = Paths.get(resourcePath.toString(), imgName);
            fileNames.append(imgName);
            Files.write(fileNameAndPath, image.getBytes());
        } else if(id != null){
            Product oldProduct = productService.findById(id);
            fileNames.append(oldProduct.getImage());
        }
        Product product = Product.builder()
                .name(name).price(new BigDecimal(price != null ? price : "0"))
                .discountPrice(new BigDecimal(discountPrice != null ? discountPrice : "0"))
                .unit(unit).quantity(quantity).remaningQuantity(remaningQuantity)
                .image(fileNames.toString()).description(description)
                .category(category).modifiedDate(LocalDate.now())
                .build();
        if(id != null){
            product.setId(id);
        }else {
            product.setCreatedDate(LocalDate.now());
        }
        productService.save(product);
        return "redirect:/admin/product/create-product";
    }




    @GetMapping("/create-product")
    public String createProduct(ModelMap model) {
        model.addAttribute("categories", categoryService.findAll());
        return "admin/create/create-product";
    }

    @ModelAttribute
    public void commonUser(Principal p, Model m, HttpSession session) {
        if (p != null) {
            String username = p.getName();
            User user = userService.findByUsername(username);
            if (user != null) {
                session.setAttribute("user", user);
                m.addAttribute("user", user);
            }


        }
    }
}
