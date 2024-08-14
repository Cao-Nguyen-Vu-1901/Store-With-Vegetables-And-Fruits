package com.cuahangnongsan.controller.web;

import com.cuahangnongsan.config.firebase.FirebaseInitializer;
import com.cuahangnongsan.constant.StringConstant;
import com.cuahangnongsan.dto.request.UserUpdateRequest;
import com.cuahangnongsan.dto.response.UserResponse;
import com.cuahangnongsan.entity.User;
import com.cuahangnongsan.service.ICategoryService;
import com.cuahangnongsan.service.IProductService;
import com.cuahangnongsan.service.IRoleService;
import com.cuahangnongsan.service.IUserService;
import com.cuahangnongsan.util.ProcessImage;
import com.google.cloud.storage.*;
import com.google.firebase.cloud.StorageClient;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.HashSet;

import static com.cuahangnongsan.util.ProcessImage.reduceSize;
import static com.cuahangnongsan.util.ProcessImage.upload;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/contact")
    public String contactUsPage(ModelMap model) {
        model.addAttribute("pageCurr", "contact");
        return "web/contact";
    }

    @GetMapping("/profile")
    public String profile(ModelMap model) {
        model.addAttribute("pageCurr", "");
        return "web/profile";
    }

    @GetMapping("/change-information")
    public String changeInformation(ModelMap model) {
        model.addAttribute("pageCurr", "");
        return "web/change-information";
    }

    //    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";
    @PostMapping("/change-information")
    public String saveInformation(UserUpdateRequest request, ModelMap model) throws IOException {
        model.addAttribute("pageCurr", "");
        User user = (User) model.getAttribute("user");
        model.remove("user");
        model.addAttribute("user", userService.updateInformation(request, user,model));
        return "web/change-information";
    }


    @GetMapping("/change-password")
    public String changePassword(ModelMap model) {
        model.addAttribute("pageCurr", "");
        return "web/change-password";
    }


    @PostMapping("/change-password")
    public String savePassword(ModelMap model, String oldPassword, String newPassword,
                               String reNewPassword) {
        model.addAttribute("pageCurr", "");
        model.remove("user");
        model.addAttribute("user", userService.updatePassword(oldPassword,newPassword,
                reNewPassword, (User) model.getAttribute("user"),model));

        return "web/change-password";
    }
    @ModelAttribute
    public void commonUser(Principal p, Model m) {
        if (p != null) {
            String username = p.getName();
            UserResponse user = userService.findByUsername(username);
            if (user != null)
                m.addAttribute("user", user);
        }
    }

}
