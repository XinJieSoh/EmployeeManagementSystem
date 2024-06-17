package com.soh.EmployeeManagementSystem.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SpringSecurityConfig {

    @Value("${spring.security.user.password}")
    private String password;

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.builder().username("adminEMS").password(passwordEncoder().encode(password)).roles("USER").build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
