package com.cuahangnongsan.controller.web;

import com.cuahangnongsan.entity.User;
import com.cuahangnongsan.service.IUserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/error")
public class CustomErrorController implements ErrorController {
    @Autowired
    private IUserService userService;
    @GetMapping
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "/error/404";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return "/error/403";
            }
        }
        return "/error/error";
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

