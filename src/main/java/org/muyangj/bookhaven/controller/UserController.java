package org.muyangj.bookhaven.controller;

import org.muyangj.bookhaven.model.User;
import org.muyangj.bookhaven.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerUser(User user, Model model) {
        System.out.println("User from form: " + user);

        try {
            User registeredUser = userService.registerUser(user);
            model.addAttribute("message", "A verification mail has been sent to " + user.getEmail());
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/verify")
    public String verifyUser(@RequestParam("token") String token, Model model){
        boolean verified = userService.verifyUser(token);

        if (verified){
            model.addAttribute("message", "mail verified successfully. You can now log in");
            return "redirect:/login";
        }else {
            model.addAttribute("error", "Invalid or expired token");
            return "redirect:/register";
        }
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email) {
        User user = userService.findByEmail(email);
        if (user != null) {
            userService.sendPasswordResetEmail(user);
        }
        return "redirect:/login?resetPassword";
    }

    @GetMapping("/reset-password")
    public String resetPassword(@RequestParam("token") String token, Model model) {
        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String processResetPassword(@RequestParam("token") String token, @RequestParam("newPassword") String newPassword, Model model) {
        boolean result = userService.resetPassword(token, newPassword);

        if (result) {
            model.addAttribute("message", "Password reset successfully. You can now log in.");
            return "redirect:/login?passwordReset";
        } else {
            model.addAttribute("error", "Invalid or expired token.");
            return "redirect:/reset-password?error";
        }
    }

    @GetMapping("/logout-success")
    public String logoutSuccess() {
        return "login";
    }
}
