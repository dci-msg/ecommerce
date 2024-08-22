package org.dci.bookhaven.service;


import org.dci.bookhaven.model.User;
import org.dci.bookhaven.repository.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Loading user by email " + email);  //--->log

        User user = userRepository.findByEmail(email);
        if (user == null){
            System.out.println("User not found: " + email);   //--->log
            throw new UsernameNotFoundException("User not found with email " + email);
        }
        if (user.getUserType() == null){
            System.out.println("User type is null for email: " + email); //--->log
        }
        System.out.println("User found: " + user.getEmail()); //--->log

        return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getUserType().getUserTypeName())
                .disabled(!user.isActive())
                .build();
    }
}
