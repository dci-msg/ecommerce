package org.dci.bookhaven.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerController {

    @GetMapping("/dashboard")
    public String showCustomerPage(){
        return "dashboard";
    }
}
