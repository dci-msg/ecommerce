package org.dci.bookhaven.config;

import org.dci.bookhaven.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final AuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    public SecurityConfig(CustomUserDetailsService customUserDetailsService, AuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        this.customUserDetailsService = customUserDetailsService;
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }

    //OPEN URLs for everyone
    private static final String[] PUBLIC_URLS = {
            "/",
            "/search",
            "/searching",
            "/searching/*",
            "/search/*",
            "/searchByKey",
            "/searchByKey/*",
            "/register",
            "/register/new",
            "/verify-email",
            "/forgot-password",
            "/reset-password",
            "/webjars/**",
            "/resources/**",
            "/css/**",
            "/js/**",
            "/images/**",
            "/book/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authenticationProvider(authenticationProvider());

        // HTTP Security structure
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers(PUBLIC_URLS).permitAll();      // open to everyone
            auth.requestMatchers("/admin/**").hasAuthority("Admin");
            auth.requestMatchers("/dashboard/**").hasAuthority("Customer");
            auth.requestMatchers("/cart/**").authenticated();
            auth.anyRequest().authenticated();                  // all other requests required authentication
        });

        //LOGIN Page structure - with usage of customAuthentication
        http.formLogin(form -> form
                    .loginPage("/login")        // custom login page
                    .usernameParameter("email") // login.html-->name="email", but should accept email as a username
                    .permitAll()                // can anyone access
                    .successHandler(customAuthenticationSuccessHandler))  //after success authentication conditions
                .logout(logout -> {
                    logout.logoutUrl("/logout");                // logout URL
                    logout.logoutSuccessUrl("/");   // after success logout direction

                }).csrf(csrf -> csrf.disable());  //csrf protection disabled

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);   //customUSerDetailsService usage here
        authProvider.setPasswordEncoder(passwordEncoder());             // BCrypt usage for encoding
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // for BCrypt algorithm
    }
}
