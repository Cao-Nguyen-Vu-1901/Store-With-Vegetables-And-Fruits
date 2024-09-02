package com.cuahangnongsan.controller.web;


import com.cuahangnongsan.dto.request.UserRequest;
import com.cuahangnongsan.exception.AppException;
import com.cuahangnongsan.service.IUserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.cuahangnongsan.dto.response.*;
@Controller
public class AuthenticationController {

    @Autowired
    private IUserService userService;

    @GetMapping("/login")
    public String login() {
        return "login-register/login";
    }

	@GetMapping("/register")
	public String register() {
		return "login-register/register";
	}

    @PostMapping("/save-user")
    public String register(@ModelAttribute UserRequest userRequest, @RequestParam("rePassword")String rePassword, HttpSession session) {

        try{
            userService.registerUser(userRequest, rePassword,session);
        }catch (AppException appException){
            return "redirect:/register";
        }
        return "redirect:/register";
    }

}
