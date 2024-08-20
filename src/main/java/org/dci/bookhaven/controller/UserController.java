package org.dci.bookhaven.controller;



import org.dci.bookhaven.model.User;
import org.dci.bookhaven.model.VerificationToken;
import org.dci.bookhaven.repository.RoleRepository;
import org.dci.bookhaven.repository.UserRepository;
import org.dci.bookhaven.repository.VerificationRepository;
import org.dci.bookhaven.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private VerificationRepository verificationRepository;



    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
       model.addAttribute("user", new User());
       return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setVerified(false);

        //assign USER role
        user.setRoles(Set.of(roleRepository.findByName("ROLE_USER")));
        //user.setRoles(Collections.singleton(roleRepository.findByName("ROLE_USER")));
        userRepository.save(user);

        //send verification email
        String token = UUID.randomUUID().toString();
        String url = "http://localhost:3636/verify?token=" + token;

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("webtestgzm@gmail.com");
        mailMessage.setText("To confirm your account, please click here: " + url);

        mailSender.send(mailMessage);

        model.addAttribute("message", "A verification mail has been sent to " + user.getEmail());

        return "register";
    }

    @GetMapping("/verify")
    public String verifyUser(@RequestParam("token") String token){

        //find the token into the db
        VerificationToken verificationToken = verificationRepository.findByToken(token);
        if (verificationToken == null){
            return "error";
        }
        //check expiry date
        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())){
            return "error";
        }

        //token is valid assign it to the user
        User user = verificationToken.getUser();
        user.setVerified(true);
        userRepository.save(user);

        //delete token
        verificationRepository.delete(verificationToken);

        //now it is verified, back to the login page
        return "redirect:/login?verified";

    }



    @GetMapping("/logout-success")
    public String logoutSuccess() {
        return "login";
    }
}
