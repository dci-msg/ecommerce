package org.dci.bookhaven.controller;

import jakarta.validation.Valid;
import org.dci.bookhaven.model.Users;
import org.dci.bookhaven.service.UsersService;
import org.dci.bookhaven.service.UsersTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class UsersController {
    private final UsersTypeService usersTypeService;
    private final UsersService usersService;
    @Autowired
    public UsersController(UsersTypeService usersTypeService, UsersService usersService) {
        this.usersTypeService = usersTypeService;
        this.usersService = usersService;
    }

    // GET method to show the login page
    @GetMapping("/login")
    public String login(){
        return "login";
    }


    // GET method to show the registration page
    @GetMapping("/register")
    public String register(Model model){

        model.addAttribute("user", new Users());
        return "register";

    }

    // POST method to handle the registration of new users
    @PostMapping("/register/new")
    public String userRegistration(@Valid Users users){

        Users savedUser = usersService.registerNewUserAccount(users);
        return "redirect:/login?registered=true";

    }

    // GET method for email verification
    @GetMapping("/verify-email")
    public String verifyEmail(@RequestParam("token") String token) {
        usersService.verifyUser(token);
        return "redirect:/login?verified=true";
    }


}







