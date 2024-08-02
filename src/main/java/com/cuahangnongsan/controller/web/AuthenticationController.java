package com.cuahangnongsan.controller.web;


import com.cuahangnongsan.entity.User;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login(ModelMap model) {
        return "login-register/login";
    }

	@GetMapping("/register")
	public String register(ModelMap model) {
		return "login-register/register";
	}

    @PostMapping("/save-user")
    public String register(@ModelAttribute User userRequest, @RequestParam("rePassword")String rePassword, HttpSession session) {

        if(!userRequest.getPassword().equals(rePassword)){
            session.setAttribute("msg", "Mật khẩu và mật khẩu nhập lại không đúng");

        }else {
            User user = userService.findByUsername(userRequest.getUsername());
            if(user != null){
                session.setAttribute("msg", "Tên đăng nhập đã tồn tại");
            }else {
                userService.save(User.builder().name(userRequest.getName()).username(userRequest.getUsername())
                        .email(userRequest.getEmail()).phoneNumber(userRequest.getPhoneNumber())
                        .password(passwordEncoder.encode(userRequest.getPassword())).address("")
                        .image("https://firebasestorage.googleapis.com/v0/b/website-ban-nong-san.appspot.com/o/users%2Fuser-avatar.jpg?alt=media&token=722a0001-b720-4398-9e32-56090424f53c").build());
                session.setAttribute("msg", "Đăng ký thành công! Vui lòng đăng nhập lại");
            }
        }


        return "redirect:/register";
    }


}
