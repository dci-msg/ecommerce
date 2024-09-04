package org.dci.bookhaven.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class HomeController {

    public String home(Model model){

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    boolean isLoggedIn = authentication != null && authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser");

    model.addAttribute("isLoggedIn", isLoggedIn);


        return "index";
    }
}
