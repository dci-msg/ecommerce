package org.dci.bookhaven.service;

import org.dci.bookhaven.model.Address;
import org.dci.bookhaven.model.User;
import org.dci.bookhaven.model.UserProfile;
import org.dci.bookhaven.model.UserType;
import org.dci.bookhaven.repository.AddressRepository;
import org.dci.bookhaven.repository.UserProfileRepository;
import org.dci.bookhaven.repository.UserRepository;
import org.dci.bookhaven.repository.UserTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UserTypeRepository userTypeRepository;
    private final PasswordEncoder passwordEncoder;
    private final AddressRepository addressRepository;

    private final UserProfileRepository userProfileRepository;
    @Autowired
    public DataInitializer(UserRepository userRepository, UserTypeRepository userTypeRepository, PasswordEncoder passwordEncoder, AddressRepository addressRepository, UserProfileRepository userProfileRepository) {
        this.userRepository = userRepository;
        this.userTypeRepository = userTypeRepository;
        this.passwordEncoder = passwordEncoder;
        this.addressRepository = addressRepository;
        this.userProfileRepository = userProfileRepository;
    }



    // ADMIN defined as "active" (default) no validation required
    @Override
    public void run(String... args) throws Exception {

        // Admin type
        UserType adminType = userTypeRepository.findByUserTypeName("Admin");
        if (adminType == null) {
            adminType = new UserType();
            adminType.setUserTypeName("Admin");
            userTypeRepository.save(adminType);
        }

        if (userRepository.findByEmail("admin@example.com") == null) {
            User admin = new User();
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setActive(true);
            admin.setUserType(adminType);
            userRepository.save(admin);
        }

        // Customer type
        UserType customerType = userTypeRepository.findByUserTypeName("Customer");
        if (customerType == null) {
            customerType = new UserType();
            customerType.setUserTypeName("Customer");
            userTypeRepository.save(customerType);
        }

        // Adding default users
        User customer1 = null;
        if (userRepository.findByEmail("customer1@example.com") == null) {
            customer1 = new User();
            customer1.setEmail("customer1@example.com");
            customer1.setPassword(passwordEncoder.encode("customer123"));
            customer1.setActive(true);
            customer1.setUserType(customerType);
            userRepository.save(customer1);
        }

        if (userRepository.findByEmail("customer2@example.com") == null) {
            User customer2 = new User();
            customer2.setEmail("customer2@example.com");
            customer2.setPassword(passwordEncoder.encode("customer456"));
            customer2.setActive(true);
            customer2.setUserType(customerType);
            userRepository.save(customer2);
        }

/*        // Create a UserProfile for the first customer
        UserProfile customer1Profile = new UserProfile("Customer", "One", LocalDate.of(1990, 2, 15));
        customer1Profile.setUser(customer1);
        userProfileRepository.save(customer1Profile);

        // Add addresses for the first customer
        Address customer1Address1 = new Address("789 Customer Rd", "Customer City", "22222", "USA");
        customer1Address1.setUserProfile(customer1Profile);
        addressRepository.save(customer1Address1);*/


    }
}
