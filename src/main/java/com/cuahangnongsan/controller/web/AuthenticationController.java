package com.cuahangnongsan.controller.web;


import com.cuahangnongsan.constant.StringConstant;
import com.cuahangnongsan.dto.request.UserRequest;
import com.cuahangnongsan.dto.response.UserResponse;
import com.cuahangnongsan.entity.User;
import com.cuahangnongsan.exception.AppException;
import com.cuahangnongsan.service.IUserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class AuthenticationController {

    @Autowired
    private IUserService userService;


    @GetMapping("/login")
    public String login(ModelMap model, String error) {
        return "login-register/login";
    }

	@GetMapping("/register")
	public String register(ModelMap model) {
		return "login-register/register";
	}

    @PostMapping("/save-user")
    public String register(@ModelAttribute UserRequest userRequest, @RequestParam("rePassword")String rePassword, HttpSession session) {

        try{
            UserResponse response = userService.registerUser(userRequest, rePassword,session);
        }catch (AppException appException){
            return "redirect:/register";
        }
        return "redirect:/register";
    }


}
