package org.dci.bookhaven.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String showAdminPage(){
        return "redirect:/manage-books";
    }
}
