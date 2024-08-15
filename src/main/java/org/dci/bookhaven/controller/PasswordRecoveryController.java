package org.dci.bookhaven.controller;

import jakarta.persistence.GeneratedValue;
import org.dci.bookhaven.model.PasswordResetToken;
import org.dci.bookhaven.model.User;
import org.dci.bookhaven.repository.PasswordResetRepository;
import org.dci.bookhaven.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.UUID;

@Controller
public class PasswordRecoveryController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private PasswordResetRepository passwordResetRepository;


    @GetMapping("/forgot-password")
    public String showForgotPasswordForm(Model model){
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, Model model){
        User user = userRepository.findByEmail(email);

        if (user == null){
            model.addAttribute("error", "No account found with that email address.");
            return "redirect:/forgot-password";
        }

        // generate reset token and send email
        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken(token, user, LocalDateTime.now().plusHours(1));
        passwordResetRepository.save(resetToken);

        String url = "http://localhost:3636/reset-password?token=" + token;

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Password Reset Request");
        mailMessage.setFrom("webtestgzm@gmail.com");
        mailMessage.setText("To reset your password, click here: " + url);

        mailSender.send(mailMessage);

        model.addAttribute("message", "A password reset link has been sen to " + email);
        return "redirect:/forgot-password";

    }

    @GetMapping("reset-password")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model){
        // find the token into the db
        PasswordResetToken passwordResetToken = passwordResetRepository.findByToken(token);
        //first check
        if (passwordResetToken == null){
            // token is not valid - add error message and sent it to the error page
            model.addAttribute("message", "Invalid Token");
            return "error";
        }
        //second check
        if (passwordResetToken.getExpiryDate().isBefore(LocalDateTime.now())){
            // token is not valid - add error message and sent it to the error page
            model.addAttribute("message", "Token expired");
            return "error";
        }
        //token is ok, send the password reset page
        model.addAttribute("token", token);
        return "reset-password";
    }




    // create a new password for the user - find the user with token(coming from user), refresh the password and save user
    @PostMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam("token") String token, @RequestParam("password") String password, Model model){
        // find the token into the db
        PasswordResetToken passwordResetToken = passwordResetRepository.findByToken(token);
        // token is not valid - add error message and sent it to the error page
        if (passwordResetToken == null){
            model.addAttribute("message", "Invalid Token");
            return "error";
        }
        // token is not valid - add error message and sent it to the error page
        if (passwordResetToken.getExpiryDate().isBefore(LocalDateTime.now())){
         model.addAttribute("message", "Token expired");
         return "error";
        }
        // find user
        User user = passwordResetToken.getUser();
        // encode the new password
        user.setPassword(passwordEncoder.encode(password));
        // save the user with new password
        userRepository.save(user);
        // delete token
        passwordResetRepository.delete(passwordResetToken);

        // direct to the login page
        model.addAttribute("message", "Password successfully reset");
        return "redirect:/login?resetSuccess";

    }
}
