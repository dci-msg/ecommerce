package org.dci.bookhaven.service;

import org.dci.bookhaven.model.Users;
import org.dci.bookhaven.model.UsersType;
import org.dci.bookhaven.model.VerificationToken;
import org.dci.bookhaven.repository.UsersRepository;
import org.dci.bookhaven.repository.UsersTypeRepository;
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
    private final UsersTypeRepository usersTypeRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UsersService(UsersRepository usersRepository, VerificationTokenRepository tokenRepository, JavaMailSender mailSender, UsersTypeRepository usersTypeRepository, BCryptPasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.tokenRepository = tokenRepository;
        this.mailSender = mailSender;
        this.usersTypeRepository = usersTypeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // REGISTER new user
    public Users registerNewUserAccount(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(false);    // until email verification provided it should be inactive

        // assign "Customer" user type
        UsersType customerType = usersTypeRepository.findByUserTypeName("Customer");
        if (customerType == null){
            throw new IllegalArgumentException(("Customer user type not found"));
        }
        user.setUsersType(customerType);

        Users savedUser = usersRepository.save(user);

        sendVerificationEmail(savedUser);    // verification email send
        return savedUser;
    }

    // VERIFICATION EMAIL
    private void sendVerificationEmail(Users savedUser) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();   // create token
        verificationToken.setToken(token);
        verificationToken.setUser(savedUser);
        verificationToken.setExpiryDate(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000));

        tokenRepository.save(verificationToken);   // save token

        String recipientAddress = savedUser.getEmail();
        String subject = "Email Confirmation";
        String confirmationUrl = "/verify-email?token=" + token;
        String message = "Please confirm your email by clicking the link below:\n";

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + "http://localhost:3636" + confirmationUrl);

        mailSender.send(email); // send token
    }

    public void verifyUser(String token) {
        VerificationToken verificationToken = tokenRepository.findByToken(token);  // after click, search if token in the db

        if (verificationToken != null && verificationToken.getExpiryDate().after(new Date())) {
            Users verifiedUser = verificationToken.getUser();
            verifiedUser.setActive(true);   // -----> now new user is active
            usersRepository.save(verifiedUser);
        }
    }


}
