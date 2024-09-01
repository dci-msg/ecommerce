package org.dci.bookhaven.config.data;

import org.dci.bookhaven.model.User;
import org.dci.bookhaven.model.UserType;
import org.dci.bookhaven.repository.UserRepository;
import org.dci.bookhaven.repository.UserTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Order(DataInitOrder.USER)
public class UserDataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UserTypeRepository userTypeRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDataInitializer(UserRepository userRepository, UserTypeRepository userTypeRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userTypeRepository = userTypeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ADMIN defined as "active" (default) no validation required
    @Override
    public void run(String... args) throws Exception {
        // Admin type
        UserType adminType = userTypeRepository.findByUserTypeName("Admin");
        if (adminType == null){
            adminType = new UserType();
            adminType.setUserTypeName("Admin");
            userTypeRepository.save(adminType);
        }

        if (userRepository.findByEmail("admin@example.com") == null){
            User admin = new User();
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setActive(true);
            admin.setUserType(adminType);
            userRepository.save(admin);
        }

        // Customer type
        UserType customerType = userTypeRepository.findByUserTypeName("Customer");
        if (customerType == null){
            customerType = new UserType();
            customerType.setUserTypeName("Customer");
            userTypeRepository.save(customerType);
        }
    }
}
