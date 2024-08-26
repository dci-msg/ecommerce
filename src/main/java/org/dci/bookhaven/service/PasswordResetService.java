package org.dci.bookhaven.service;

import org.dci.bookhaven.model.PasswordResetToken;
import org.dci.bookhaven.model.User;
import org.dci.bookhaven.repository.PasswordResetTokenRepository;
import org.dci.bookhaven.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetService {
    @Value("${app.domain}")
    private String DOMAIN;

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final JavaMailSender mailSender;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public PasswordResetService(UserRepository userRepository, PasswordResetTokenRepository passwordResetTokenRepository, JavaMailSender mailSender, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
    }

    public void sendPasswordResetEmail(String email){
        User user = userRepository.findByEmail(email);
        if (user == null){

            return;
        }

        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(LocalDateTime.now().plusHours(1));  // 1 hour valid

        passwordResetTokenRepository.save(resetToken);

        String resetUrl = DOMAIN + "/reset-password?token=" + token;
        String subject = "Password Reset Request";
        String message = "To reset your password, please click the following link: " + resetUrl;

        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setTo(email);
        emailMessage.setSubject(subject);
        emailMessage.setText(message);

        mailSender.send(emailMessage);
    }

    public boolean validatePasswordResetToken(String token){
        Optional<PasswordResetToken> resetToken = passwordResetTokenRepository.findByToken(token);
        return resetToken.isPresent() && resetToken.get().getExpiryDate().isAfter(LocalDateTime.now());
    }

    public boolean resetPassword(String token, String newPassword){
        Optional<PasswordResetToken> resetTokenOpt = passwordResetTokenRepository.findByToken(token);
        if (resetTokenOpt.isPresent() && validatePasswordResetToken(token)){

            PasswordResetToken resetToken = resetTokenOpt.get();
            User user = resetToken.getUser();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            // then delete token which used
            passwordResetTokenRepository.delete(resetToken);
            return true;
        }
        return false;
    }


}
