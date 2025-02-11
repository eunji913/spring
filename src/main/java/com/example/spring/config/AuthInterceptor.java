package com.example.spring.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.lang.NonNull;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
        @NonNull HttpServletRequest request, 
        @NonNull HttpServletResponse response, 
        @NonNull Object handler
    ) throws Exception {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("isLoggedIn") == null) {
            response.sendRedirect("/auth/login?error=auth");
            return false;
        }

        return true;
    }
}
