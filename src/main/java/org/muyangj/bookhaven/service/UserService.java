package org.muyangj.bookhaven.service;


import jakarta.transaction.Transactional;
import org.muyangj.bookhaven.model.PasswordResetToken;
import org.muyangj.bookhaven.model.User;
import org.muyangj.bookhaven.model.VerificationToken;
import org.muyangj.bookhaven.repository.PasswordResetRepository;
import org.muyangj.bookhaven.repository.UserRepository;
import org.muyangj.bookhaven.repository.VerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VerificationRepository tokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private PasswordResetRepository passwordResetRepository;

    @Transactional
    public User registerUser(User user) {
        // check if user with the same email already exists
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Email is already in use.");
        }

        System.out.println("Registering user: " + user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user); // first, save the user

        sendVerificationEmail(savedUser); // send verification email
        return savedUser;

    }

    private void sendVerificationEmail(User user) {
        try {
            String token = UUID.randomUUID().toString();
            VerificationToken verificationToken = new VerificationToken(token, user, LocalDateTime.now().plusDays(1));
            tokenRepository.save(verificationToken);

            String recipientAddress = user.getEmail();
            String subject = "Email Verification";
            String confirmationUrl = "http://localhost:3030/verify?token=" + token;
            String message = "Please click the link below to VERIFY your email:\n" + confirmationUrl;

            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(recipientAddress);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(message);

            mailSender.send(simpleMailMessage);
            System.out.println("Verification email sent to: " + recipientAddress);
        } catch (Exception e) {
            System.err.println("Failed to send verification email: " + e.getMessage());
        }
    }

    // verify user
    @Transactional
    public boolean verifyUser(String token) {
        VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null || verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return false;
        }
        User user = verificationToken.getUser();
        user.setVerified(true);
        userRepository.save(user);
        tokenRepository.delete(verificationToken);
        return true;

    }


    public void sendPasswordResetEmail(User user) {
        String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = new PasswordResetToken(token, user, LocalDateTime.now().plusDays(1));
        passwordResetRepository.save(passwordResetToken);

        String recipientAddress = user.getEmail();
        String subject = "Password Reset";
        String resetUrl = "http://localhost:3030/reset-password?token=" + token;
        String message = "Please click the link below to RESET your email:\n" + resetUrl;

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(recipientAddress);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);

        mailSender.send(simpleMailMessage);
    }

    // reset password
    @Transactional
    public boolean resetPassword(String token, String newPassword) {
        PasswordResetToken passwordResetToken = passwordResetRepository.findByToken(token);
        if (passwordResetToken == null || passwordResetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return false;
        }
        User user = passwordResetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        passwordResetRepository.delete(passwordResetToken);
        return true;
    }


    // find user by email
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
