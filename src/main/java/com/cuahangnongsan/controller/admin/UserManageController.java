package com.cuahangnongsan.controller.admin;

import com.cuahangnongsan.constant.StringConstant;
import com.cuahangnongsan.entity.Role;
import com.cuahangnongsan.entity.User;
import com.cuahangnongsan.service.IRoleService;
import com.cuahangnongsan.service.IUserService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/user")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserManageController {
    @Autowired
    IUserService userService;
    @Autowired
    IRoleService roleService;

    @GetMapping("/manage-user")
    public String showUsers(ModelMap modelMap, String type, String value) {
        List<User> users = new ArrayList<>();
        List<Role> roles = roleService.findAllByName("ROLE_USER");
        try {
            if (type != null && value != null) {
                value = value.trim();
                users = switch (type) {
                    case "name" -> userService.findAllByNameLike("%" + value + "%");
                    case "dob" -> userService.findAllByDob(LocalDate.parse(value));
                    case "gender" -> userService.findAllByGender(value);
                    case "email" -> userService.findAllByEmailLike("%" + value + "%");
                    case "address" -> userService.findAllByAddressLike("%" + value + "%");
                    case "status" -> userService.findAllByStatus(value);
                    default -> userService.findAllByRoleEqualUser(roles);
                };
            } else {
                users = userService.findAllByRoleEqualUser(roles);
            }
        } catch (Exception e) {
            users = null;
        }
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
}
