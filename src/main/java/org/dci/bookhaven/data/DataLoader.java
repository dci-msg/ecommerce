package org.dci.bookhaven.data;

import org.dci.bookhaven.model.Role;
import org.dci.bookhaven.model.User;
import org.dci.bookhaven.repository.RoleRepository;
import org.dci.bookhaven.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        // create roles
        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");
        roleRepository.save(adminRole);

        // create admin users
        User admin1 = new User();
        admin1.setFirstName("Gizem");
        admin1.setLastName("Turhan");
        admin1.setUsername("admin1");
        admin1.setPassword(passwordEncoder.encode("adminpass1"));
        admin1.setEmail("zuehre.turhan@dci-student.org");
        admin1.setVerified(true);
        admin1.setRoles(Collections.singleton(adminRole));
        userRepository.save(admin1);

        /*User admin2 = new User();
        admin2.setFirstName("MuYang");
        admin2.setLastName("Zander");
        admin2.setUsername("admin2");
        admin2.setPassword(passwordEncoder.encode("adminpass2"));
        admin2.setEmail("muyang.zander@dci-student.org");
        admin2.setVerified(true);
        admin2.setRoles(Collections.singleton(adminRole));
        userRepository.save(admin2);

        User admin3 = new User();
        admin3.setFirstName("Terissa");
        admin3.setLastName("Gwendjo");
        admin3.setUsername("admin3");
        admin3.setPassword(passwordEncoder.encode("adminpass3"));
        admin3.setEmail("terissa.gwendjo@dci-student.org");
        admin3.setVerified(true);
        admin3.setRoles(Collections.singleton(adminRole));
        userRepository.save(admin3);

        User admin4 = new User();
        admin4.setFirstName("Shoghik");
        admin4.setLastName("Kachatryan");
        admin4.setUsername("admin4");
        admin4.setPassword(passwordEncoder.encode("adminpass4"));
        admin4.setEmail("shoghik.kachatryan@dci-student.org");
        admin4.setVerified(true);
        admin4.setRoles(Collections.singleton(adminRole));
        userRepository.save(admin4);*/



    }
}
