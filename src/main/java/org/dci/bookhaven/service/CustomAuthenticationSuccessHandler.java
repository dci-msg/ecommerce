package org.dci.bookhaven.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // according to user type, direct them (after authentication comes here)
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Admin"))){
            response.sendRedirect("/admin/dashboard");
        }else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Customer"))){
            response.sendRedirect("/index");
        }else {
            response.sendRedirect("/login?error");
        }
    }
}
