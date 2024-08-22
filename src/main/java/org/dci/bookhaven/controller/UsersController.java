package org.dci.bookhaven.controller;


import jakarta.validation.Valid;
import org.dci.bookhaven.model.User;
import org.dci.bookhaven.service.UserService;
import org.dci.bookhaven.service.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UsersController {
    private final UserTypeService userTypeService;
    private final UserService userService;

    @Autowired
    public UsersController(UserTypeService userTypeService, UserService userService) {
        this.userTypeService = userTypeService;
        this.userService = userService;
    }

    // GET method to show the login page
   /* @GetMapping("/login")
    public String login(){
        return "login";
    }*/

    @GetMapping("/login")
    public String login(@RequestParam(value = "registered", required = false) boolean registered,
                        @RequestParam(value = "verified", required = false) boolean verified,
                        Model model) {
        if (registered) {
            model.addAttribute("successMessage", "Registration successful! Please check your email to confirm your account.");
        } else if (verified) {
            model.addAttribute("successMessage", "Email verified successfully! You can now log in.");

        }
        return "login";
    }


    // GET method to show the registration page
    @GetMapping("/register")
    public String register(Model model) {

        model.addAttribute("user", new User());
        return "register";

    }

    // POST method to handle the registration of new users
    @PostMapping("/register/new")
    public String userRegistration(@Valid User user, Model model) {
        try {
            User savedUser = userService.registerNewUserAccount(user);
            return "redirect:/login?registered=true";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "register";
        }
    }

    // GET method for email verification
    @GetMapping("/verify-email")
    public String verifyEmail(@RequestParam("token") String token) {
        userService.verifyUser(token);
        return "redirect:/login?verified=true";
    }


}







