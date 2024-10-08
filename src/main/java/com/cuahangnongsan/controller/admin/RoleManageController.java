package com.cuahangnongsan.controller.admin;


import com.cuahangnongsan.exception.AppException;
import com.cuahangnongsan.service.IPermissionService;
import com.cuahangnongsan.service.IRoleService;
import com.cuahangnongsan.service.IUserService;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import com.cuahangnongsan.dto.response.*;
@Controller
@RequestMapping("/admin/roles")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleManageController {
    @Autowired
    IUserService userService;

    @Autowired
    IRoleService roleService;

    @Autowired
    IPermissionService permissionService;

    @GetMapping("/manage-roles")
    public String manageRoles(ModelMap modelMap, String value) {
        modelMap.addAttribute("roles",
                value == null
                        ? roleService.findAll()
                        : roleService.findByNameLike("%" + value + "%"));
        return "admin/manage/manage-role";
    }

    @PostMapping("/manage-roles")
    public String editRoles(ModelMap modelMap, String id, String action, RedirectAttributes redirectAttributes) {
        RoleResponse role = roleService.findById(id);
        if (action.equals("edit")) {
            redirectAttributes.addFlashAttribute("role", role);
            return "redirect:/admin/roles/create-role";
        }
        return "redirect:/admin/roles/manage-roles";
    }

    @PostMapping("/manage-role-permissions")
    public String updateRole(String id
            , @RequestParam(value = "permissions[]", required = false) List<String> permissions) {
        roleService.update(id, permissions);
        return "redirect:/admin/roles/manage-roles";
    }

    @PostMapping("/save-role")
    public String saveRole(RedirectAttributes model,String id, String name,
                           @RequestParam(value = "permissionsNew[]", required = false) List<String> permissionsNew) {

        try{
            roleService.save(id, name, permissionsNew);
        }catch (AppException e){
            model.addFlashAttribute("errorName", "Tên đã tồn tại!");
            model.addFlashAttribute("role", RoleResponse.builder().name(name).build());
            return "redirect:/admin/roles/create-role";
        }
        return "redirect:/admin/roles/create-role";
    }

    @GetMapping("/create-role")
    public String createRole(ModelMap modelMap) {
        RoleResponse role = (RoleResponse) modelMap.getAttribute("role");
        modelMap.addAttribute("role", role);
        modelMap.addAttribute("permissions", permissionService.findAll());
        return "admin/create/create-role";
    }

    @ModelAttribute
    public void commonUser(Principal p, Model m, HttpSession session) {
        if (p != null) {
            String username = p.getName();
            UserResponse user = userService.findByUsername(username);
            if (user != null) {
                m.addAttribute("user", user);
            }
        }
    }
}
