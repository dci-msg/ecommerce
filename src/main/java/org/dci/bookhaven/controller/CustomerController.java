package org.dci.bookhaven.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerController {

    @GetMapping("/dashboard")
    public String showCustomerPage(Model model){
        return "dashboard";
    }
}

 /*   public String showCustomerPage(Model model, Authentication authentication) {
        Long userId = ((UserDetails) authentication.getPrincipal()).getId();
        UserProfile userProfile = userProfileService.getLoggedInUserProfile(userId);
        model.addAttribute("UserProfile", userProfile);

        return "dashboard";
    }*/
