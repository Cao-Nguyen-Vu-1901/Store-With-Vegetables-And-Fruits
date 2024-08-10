package com.cuahangnongsan.controller.admin;

import com.cuahangnongsan.constant.StringConstant;
import com.cuahangnongsan.entity.Permission;
import com.cuahangnongsan.entity.Role;
import com.cuahangnongsan.entity.User;
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
        List<User> users = new ArrayList<>();
        try {
            if (type != null && value != null) {
                value = value.trim();
                users = switch (type) {
                    case "name" -> userService.findAllByNameLike("%" + value + "%");
                    case "email" -> userService.findAllByEmailLike("%" + value + "%");
                    case "address" -> userService.findAllByAddressLike("%" + value + "%");
                    default -> null;
                };
            } else {
                users = userService.findAllByRoleName(
                        role != null && role.equals("admin") ? StringConstant.ROLE_ADMIN : StringConstant.ROLE_USER);
            }
        } catch (Exception e) {
            users = null;
        }
        modelMap.addAttribute("role", role);
        modelMap.addAttribute("users", users);
        return "admin/manage/manage-user";
    }

    @PostMapping("/manage-user")
    public String manageUser(ModelMap modelMap, String id) {
        User user = userService.findById(id);
        user.setStatus(StringConstant.USER_STATUS_DISABLE);
        userService.save(user);
        return "redirect:/admin/user/manage-user";
    }

    ///// End Manage User /////

    ///// Start Create User /////

    @GetMapping("/create-user")
    public String showViewCreate(ModelMap modelMap){
        modelMap.addAttribute("roles", roleService.findAll());
        modelMap.addAttribute("permissions", permissionService.findAll());
        modelMap.addAttribute("userNew",(User) modelMap.getAttribute("userNew"));
        return "admin/create/create-user";
    }

    @PostMapping("/save-user")
    public String saveUser(ModelMap modelMap, String name, String username, String email,
                           String phoneNumber, MultipartFile image, String optionsChoice,
                           String password, String rePassword,
                           @RequestParam(value = "roles[]" , required = false)  ArrayList<String> roles,
                           @RequestParam( value = "permissions[]" , required = false)  ArrayList<String> permissions,
                           String roleName, String cityProvince,String district, String ward,
                           String specificAddress,
                           RedirectAttributes redirectAttributes) throws IOException {
        String imageName = "";
        Set<Role> listRole = new HashSet<>();
        User userRedirect = User.builder()
                .name(name).username(username)
                .phoneNumber(phoneNumber).status(StringConstant.USER_STATUS_ACTIVE)
                .email(email).build();
        if(password != null && password.equals(rePassword)){
            if(Objects.equals(image.getOriginalFilename(),"")){
                redirectAttributes.addFlashAttribute("userNew", userRedirect);
                return "redirect:/admin/user/create-user";
            }else {
                imageName = ProcessImage.upload(image);
                String address = specificAddress + ", " + ward + ", " + district + ", " + cityProvince;
                if(optionsChoice.equals("yes")){
                    roles.forEach( a-> {
                        Role role = roleService.findById(a);
                        listRole.add(role);
                    });
                }else if(optionsChoice.equals("no")) {
                    Set<Permission> listPermissions = new HashSet<>();
                    permissions.forEach(a-> {
                        Permission permission = permissionService.findById(a);
                        listPermissions.add(permission);
                    });
                    Role role = Role.builder().name( "ROLE_ADMIN_" + roleName).permissions(listPermissions).build();
                    Role roleNew = roleService.save(role);

                    listRole.add(roleNew);
                }
                User user = User.builder()
                        .name(name).username(username)
                        .phoneNumber(phoneNumber).status(StringConstant.USER_STATUS_ACTIVE)
                        .email(email).address(address).failedLoginAttempts(0)
                        .password(passwordEncoder.encode(password)).image(imageName)
                        .address(address).roles(listRole)
                        .build();
                userService.save(user);
                return "redirect:/admin/user/manage-user";
            }
        }else {
            redirectAttributes.addFlashAttribute("userNew", userRedirect);
            return "redirect:/admin/user/create-user";
        }
    }

    ///// End Create User /////

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
