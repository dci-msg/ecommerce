package org.dci.bookhaven.service;

import jakarta.transaction.Transactional;
import org.dci.bookhaven.model.*;
import org.dci.bookhaven.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AnonymousAuthenticationToken;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final VerificationTokenRepository tokenRepository;
    private final JavaMailSender mailSender;
    private final UserTypeRepository userTypeRepository;
    private final PasswordEncoder passwordEncoder;

    private final UserProfileRepository userProfileRepository;
    private final AddressRepository addressRepository;

    // Define the logger for this class
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Value("${app.domain}")
    private String DOMAIN;


    @Autowired
    public UserService(UserRepository userRepository, VerificationTokenRepository tokenRepository,
                       JavaMailSender mailSender, UserTypeRepository userTypeRepository,
                       PasswordEncoder passwordEncoder, UserProfileRepository userProfileRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.mailSender = mailSender;
        this.userTypeRepository = userTypeRepository;
        this.passwordEncoder = passwordEncoder;
        this.userProfileRepository = userProfileRepository;
        this.addressRepository = addressRepository;
    }

    // REGISTER new user
    @Modifying
    @Transactional
    public User registerNewUserAccount(User user) {
        // existed user registration
        // check if user in db
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            if (existingUser.isActive()) {
                throw new IllegalArgumentException("Email address already exists.");
            } else {
                // if user is not active, update it
                existingUser.setActive(false);

                User updatedUser = userRepository.save(existingUser);
                sendVerificationEmail(updatedUser);
                return updatedUser;
            }
        }
        // new user registration
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(false);    // until email verification provided it should be inactive

        // assign "Customer" user type
        UserType customerType = userTypeRepository.findByUserTypeName("Customer");
        if (customerType == null) {
            throw new IllegalArgumentException(("Customer user type not found"));
        }
        user.setUserType(customerType);

        User savedUser = userRepository.save(user);
        // changes made to sync, create userProfile with user
        UserProfile userProfile = new UserProfile();
        userProfile.setUser(savedUser);
        userProfileRepository.save(userProfile);
        // Added logging to track UserProfile creation
        logger.info("UserProfile created with ID: {}", userProfile.getUser().getId());
        Address address = new Address();
        address.setUserProfile(userProfile);
        addressRepository.save(address);
        logger.info("Address created with ID: {}", address.getId());
        System.out.println(user);

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
        email.setText(message + DOMAIN + confirmationUrl);

        mailSender.send(email); // send token
    }

    @Modifying
    @Transactional
    public void verifyUser(String token) {
        VerificationToken verificationToken = tokenRepository.findByToken(token);  // after click, search if token in the db

        if (verificationToken != null && verificationToken.getExpiryDate().after(new Date())) {
            User verifiedUser = verificationToken.getUser();
            verifiedUser.setActive(true);   // -----> now new user is active

            userRepository.save(verifiedUser);
        }
    }


    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            return userRepository.findByEmail(username);
        }
        return null;
    }

    public boolean isLoggedIn(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser");
    }

    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        users.sort((u1, u2) -> u1.getId().compareTo(u2.getId()));
        return users;
    }
}
