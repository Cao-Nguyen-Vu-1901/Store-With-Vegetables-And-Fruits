package com.cuahangnongsan.controller.admin;

import com.cuahangnongsan.constant.StringConstant;
import com.cuahangnongsan.dto.request.AdminUserCreationRequest;
import com.cuahangnongsan.dto.response.PermissionResponse;
import com.cuahangnongsan.dto.response.RoleResponse;
import com.cuahangnongsan.dto.response.UserResponse;
import com.cuahangnongsan.entity.Permission;
import com.cuahangnongsan.entity.Role;
import com.cuahangnongsan.entity.User;
import com.cuahangnongsan.exception.AppException;
import com.cuahangnongsan.service.IPermissionService;
import com.cuahangnongsan.service.IRoleService;
import com.cuahangnongsan.service.IUserService;
import com.cuahangnongsan.util.ProcessImage;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/admin/user")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserManageController {
    @Autowired
    IUserService userService;
    @Autowired
    IRoleService roleService;
    @Autowired
    IPermissionService permissionService;

    @Autowired
    PasswordEncoder passwordEncoder;

    ///// Start Manage User /////

    @GetMapping("/manage-user")
    public String showUsers(ModelMap modelMap, String type, String value, String role) {
        userService.showAndSearchUser(modelMap, type, value, role);
        return "admin/manage/manage-user";
    }

    @PostMapping("/manage-user")
    public String manageUser(ModelMap modelMap,String id) {
        modelMap.addAttribute("user", userService.updateStatusUser(id));
        return "redirect:/admin/user/manage-user";
    }

    ///// End Manage User /////

    ///// Start Create User /////

    @GetMapping("/create-user")
    public String showViewCreate(ModelMap modelMap){
        modelMap.addAttribute("roles", roleService.findAll());
        modelMap.addAttribute("permissions", permissionService.findAll());
        modelMap.addAttribute("userNew",(UserResponse) modelMap.getAttribute("userNew"));
        return "admin/create/create-user";
    }

    @PostMapping("/save-user")
    public String saveUser( @RequestParam(value = "roles[]" , required = false)  ArrayList<String> roles,
                           @RequestParam( value = "permissions[]" , required = false)  ArrayList<String> permissions,
                           AdminUserCreationRequest request,
                           RedirectAttributes redirectAttributes) {
//        String imageName = "";
//        Set<RoleResponse> listRole = new HashSet<>();
//        User userRedirect = User.builder()
//                .name(name).username(username)
//                .phoneNumber(phoneNumber).status(StringConstant.USER_STATUS_ACTIVE)
//                .email(email).build();
//        if(password != null && password.equals(rePassword)){
//            if(userService.findByUsername(username) == null){
//                if(Objects.equals(image.getOriginalFilename(),"")){
//                    redirectAttributes.addFlashAttribute("userNew", userRedirect);
//                    redirectAttributes.addFlashAttribute("errorImage", "Vui lòng chọn ảnh!");
//                }else {
//                    imageName = ProcessImage.upload(image,"users/");
//                    String address = specificAddress + ", " + ward + ", " + district + ", " + cityProvince;
//                    if(optionsChoice.equals("yes")){
//                        roles.forEach( a-> {
//                            RoleResponse role = roleService.findById(a);
//                            listRole.add(role);
//                        });
//                    }else if(optionsChoice.equals("no")) {
//                        Set<PermissionResponse> listPermissions = new HashSet<>();
//                        permissions.forEach(a-> {
//                            PermissionResponse permission = permissionService.findById(a);
//                            listPermissions.add(permission);
//                        });
//                        if(roleService.findByName("ROLE_ADMIN_" + roleName) == null){
//                            Role role = Role.builder().name( "ROLE_ADMIN_" + roleName).permissions(listPermissions).build();
//                            RoleResponse roleNew = roleService.save(role);
//                            listRole.add(roleNew);
//                        }else {
//                            redirectAttributes.addFlashAttribute("userNew", userRedirect);
//                            redirectAttributes.addFlashAttribute("errorRole", "Tên vai trò đã tồn tại!");
//                            return "redirect:/admin/user/create-user";
//                        }
//                    }
//                    User user = User.builder()
//                            .name(name).username(username)
//                            .phoneNumber(phoneNumber).status(StringConstant.USER_STATUS_ACTIVE)
//                            .email(email).address(address).failedLoginAttempts(0)
//                            .password(passwordEncoder.encode(password)).image(imageName)
//                            .address(address).roles(listRole)
//                            .build();
//                    userService.save(user);
//                }
//            }else{
//                redirectAttributes.addFlashAttribute("userNew", userRedirect);
//                redirectAttributes.addFlashAttribute("errorUserName", "Tên đăng nhập đã tồn tại!");
//
//            }
//        }else {
//            redirectAttributes.addFlashAttribute("userNew", userRedirect);
//            redirectAttributes.addFlashAttribute("errorPassword", "Mật khẩu và mật khẩu nhập lại không trùng khớp!");
//
//        }

        try {
            userService.adminSave(roles, request, permissions,redirectAttributes);
        }catch (IOException | AppException e){
            return "redirect:/admin/user/create-user";
        }


        return "redirect:/admin/user/create-user";
    }

    ///// End Create User /////

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
