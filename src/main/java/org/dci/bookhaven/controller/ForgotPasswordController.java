package org.dci.bookhaven.controller;

import org.dci.bookhaven.model.Users;
import org.dci.bookhaven.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ForgotPasswordController {
    private final PasswordResetService passwordResetService;

    @Autowired
    public ForgotPasswordController(PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }


    // GET method to show the forgot password page
    @GetMapping("/forgot-password")
    public String showForgotPasswordForm(){
        return "forgot-password";
    }

    // POST method to handle password reset request
    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, Model model){
        passwordResetService.sendPasswordResetEmail(email);
        model.addAttribute("message", "If an account with that email exists, you will receive a password reset email.");
        return "forgot-password";
    }

    // GET method to show the password reset form
    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model){
        if (!passwordResetService.validatePasswordResetToken(token)){
            model.addAttribute("error", "Invalid or expired token");
            return "reset-password";
        }
        model.addAttribute("token", token);
        return "reset-password";

    }

    // POST method to handle new password submission
    @PostMapping("reset-password")
    public String processResetPassword(@RequestParam("token") String token, @RequestParam("password") String newPassword, Model model) {
        if (passwordResetService.resetPassword(token, newPassword)) {
            model.addAttribute("message", "Password has been reset successfully.");
            return "login";
        } else {
            model.addAttribute("message", "Password reset failed.");
            return "reset-password";
        }
    }
}
