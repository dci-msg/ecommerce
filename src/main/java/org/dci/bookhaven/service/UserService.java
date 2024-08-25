package org.dci.bookhaven.service;

import jakarta.transaction.Transactional;
import org.dci.bookhaven.model.User;
import org.dci.bookhaven.model.UserType;
import org.dci.bookhaven.model.VerificationToken;
import org.dci.bookhaven.repository.UserRepository;
import org.dci.bookhaven.repository.UserTypeRepository;
import org.dci.bookhaven.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final VerificationTokenRepository tokenRepository;
    private final JavaMailSender mailSender;
    private final UserTypeRepository userTypeRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, VerificationTokenRepository tokenRepository,
                       JavaMailSender mailSender, UserTypeRepository userTypeRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.mailSender = mailSender;
        this.userTypeRepository = userTypeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // REGISTER new user
    public User registerNewUserAccount(User user) {
        // existed user registration
        // check if user in db
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            if (existingUser.isActive()) {
                throw new IllegalArgumentException("Email address already exists.");
            } else {
                // if user is not active, update it
                //existingUser.setRegistrationDate(new Date()); ----------------delete
                existingUser.setActive(false);

                User updatedUser = userRepository.save(existingUser);
                sendVerificationEmail(updatedUser);
                return updatedUser;
            }
        }
        // new user registration
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(false);    // until email verification provided it should be inactive
        //user.setRegistrationDate(new Date()); ----------------delete

        // assign "Customer" user type
        UserType customerType = userTypeRepository.findByUserTypeName("Customer");
        if (customerType == null) {
            throw new IllegalArgumentException(("Customer user type not found"));
        }
        user.setUserType(customerType);

        User savedUser = userRepository.save(user);

        sendVerificationEmail(savedUser);    // verification email send
        return savedUser;
    }


    // VERIFICATION EMAIL
    @Transactional
    public void sendVerificationEmail(User savedUser) {
        // check and delete if token already in the db
        VerificationToken existingToken = tokenRepository.findByUser(savedUser);
        if (existingToken != null) {
            tokenRepository.delete(existingToken);
        }
        // create a new token
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
            User verifiedUser = verificationToken.getUser();
            verifiedUser.setActive(true);   // -----> now new user is active

            userRepository.save(verifiedUser);
        }
    }


}
