package com.cuahangnongsan.config.security;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        String errorMessage;
        if (exception.getMessage().contains("disabled")) {
            errorMessage = "Tài khoản đã bị vô hiệu hoá";
        } else {
            errorMessage = "Tên đăng nhập hoặc mật khẩu không đúng";
        }

        // Đặt thông báo lỗi vào session
        request.getSession().setAttribute("errorMessage", errorMessage);
        response.sendRedirect("/login?error=true");
    }
}

