package org.dci.bookhaven.service;

import org.dci.bookhaven.model.Users;
import org.dci.bookhaven.repository.UsersRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    public CustomUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Loading user by email " + email);  //log

        Users user = usersRepository.findByEmail(email);
        if (user == null){
            System.out.println("User not found: " + email);   //log
            throw new UsernameNotFoundException("User not found with email " + email);
        }
        System.out.println("User found: " + user.getEmail()); //log
        return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getUsersType().getUserTypeName())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(!user.isActive())
                .build();
    }
}
