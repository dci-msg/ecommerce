package org.dci.bookhaven.service;

import org.dci.bookhaven.model.Users;
import org.dci.bookhaven.model.UsersType;
import org.dci.bookhaven.repository.UsersRepository;
import org.dci.bookhaven.repository.UsersTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsersRepository usersRepository;
    private final UsersTypeRepository usersTypeRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataInitializer(UsersRepository usersRepository, UsersTypeRepository usersTypeRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.usersTypeRepository = usersTypeRepository;
        this.passwordEncoder = passwordEncoder;
    }


    // ADMIN defined as "active" (default) no validation required
    @Override
    public void run(String... args) throws Exception {

        // Admin type
        UsersType adminType = usersTypeRepository.findByUserTypeName("Admin");
        if (adminType == null){
            adminType = new UsersType();
            adminType.setUserTypeName("Admin");
            usersTypeRepository.save(adminType);
        }

        if (usersRepository.findByEmail("admin@example.com") == null){
            Users admin = new Users();
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setActive(true);
            admin.setUsersType(adminType);
            usersRepository.save(admin);
        }

        // Customer type
        UsersType customerType = usersTypeRepository.findByUserTypeName("Customer");
        if (customerType == null){
            customerType = new UsersType();
            customerType.setUserTypeName("Customer");
            usersTypeRepository.save(customerType);
        }

    }
}
