package org.dci.bookhaven.service;

import org.dci.bookhaven.model.Users;
import org.dci.bookhaven.model.VerificationToken;
import org.dci.bookhaven.repository.UsersRepository;
import org.dci.bookhaven.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final VerificationTokenRepository tokenRepository;
    private final JavaMailSender mailSender;

    @Autowired
    public UsersService(UsersRepository usersRepository, VerificationTokenRepository tokenRepository, JavaMailSender mailSender) {
        this.usersRepository = usersRepository;
        this.tokenRepository = tokenRepository;
        this.mailSender = mailSender;
    }

    public Users registerNewUserAccount(Users user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setActive(false);    // until email verification provided it should be inactive

        Users savedUser = usersRepository.save(user);
        sendVerificationEmail(savedUser);    // verification email send
        return savedUser;
    }

    private void sendVerificationEmail(Users savedUser) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(savedUser);
        verificationToken.setExpiryDate(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000));

        tokenRepository.save(verificationToken);

        String recipientAddress = savedUser.getEmail();
        String subject = "Email Confirmation";
        String confirmationUrl = "/verify-email?token=" + token;
        String message = "Please confirm your email bt clicking the link below:\n";

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + "http://localhost:3636" + confirmationUrl);

        mailSender.send(email);
    }

    public void verifyUser(String token) {
        VerificationToken verificationToken = tokenRepository.findByToken(token);

        if (verificationToken != null && verificationToken.getExpiryDate().after(new Date())) {
            Users user = verificationToken.getUser();
            user.setActive(true);
            usersRepository.save(user);
        }
    }


}
