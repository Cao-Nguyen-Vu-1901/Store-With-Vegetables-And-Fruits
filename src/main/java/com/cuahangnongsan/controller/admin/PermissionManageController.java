package com.cuahangnongsan.controller.admin;


import com.cuahangnongsan.dto.request.PermissionRequest;
import com.cuahangnongsan.dto.response.PermissionResponse;
import com.cuahangnongsan.entity.Permission;
import com.cuahangnongsan.entity.Role;
import com.cuahangnongsan.entity.User;
import com.cuahangnongsan.mapper.PermissionMapper;
import com.cuahangnongsan.service.IPermissionService;
import com.cuahangnongsan.service.IRoleService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin/permission")
public class PermissionManageController {

    @Autowired
    IUserService userService;

    @Autowired
    IPermissionService permissionService;

    @Autowired
    PermissionMapper permissionMapper;

    @GetMapping("/manage-permission")
    public String showPermissions(ModelMap modelMap){
        modelMap.addAttribute("permissions", permissionService.findAll());
        return "admin/manage/manage-permission";
    }
    @PostMapping("/manage-permission")
    public String managePermissions(String action, String id,
                                    RedirectAttributes redirectAttributes){
        if(action.equals("edit")){
            redirectAttributes.addFlashAttribute("permission",(PermissionResponse) permissionService.findById(id));
        }
        return "redirect:/admin/permission/create-permission";
    }

    @PostMapping("/save-permission")
    public String savePermission(String id, PermissionRequest request, RedirectAttributes redirectAttributes){

        Permission permission = permissionMapper.toPermission(request);
        permission.setId(id);

        PermissionResponse exist = permissionService.findByName(request.getName().toUpperCase());

        if(exist != null){
            redirectAttributes.addFlashAttribute("error", "Tên đã tồn tại!");
            exist.setId(null);
            redirectAttributes.addFlashAttribute("permission", exist);
        }
        permissionService.save(permission);
        return "redirect:/admin/permission/create-permission";
    }

    @GetMapping("/create-permission")
    public String createPermission(ModelMap modelMap){
        modelMap.addAttribute("permission", modelMap.getAttribute("permission"));
        modelMap.addAttribute("error", modelMap.getAttribute("error"));
        return "admin/create/create-permission";
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
