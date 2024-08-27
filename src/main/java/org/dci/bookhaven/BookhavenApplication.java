package org.dci.bookhaven;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.userdetails.User;

@SpringBootApplication
public class BookhavenApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookhavenApplication.class, args);
    }

    // Create 5 users
    CommandLineRunner runner() {
        return args -> {
            // Create 5 users
            for(int i = 0; i < 5; i++) {
                User user = new User();
                user.setUsername("user" + i);
                user.setEmail("user" + i + "@example.com");
                user.setPassword("password" + i);
                userService.save(user);
            }
        };
    }

    // Create 10 books

    // Create 5 categories

    // Create 8 addresses



}
