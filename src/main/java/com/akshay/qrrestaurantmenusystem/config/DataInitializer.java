package com.akshay.qrrestaurantmenusystem.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.akshay.qrrestaurantmenusystem.entity.User;
import com.akshay.qrrestaurantmenusystem.repository.UserRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initializeUsers(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            // ==========================================
            // CREATE ADMIN USER
            // ==========================================

            if (userRepository.findByUsername("admin").isEmpty()) {

                User admin = new User();

                admin.setUsername("admin");

                admin.setPassword(
                        passwordEncoder.encode("admin123"));

                admin.setRole("ADMIN");

                admin.setEnabled(true);

                userRepository.save(admin);
            }

            // ==========================================
            // CREATE KITCHEN USER
            // ==========================================

            if (userRepository.findByUsername("kitchen").isEmpty()) {

                User kitchen = new User();

                kitchen.setUsername("kitchen");

                kitchen.setPassword(
                        passwordEncoder.encode("kitchen123"));

                kitchen.setRole("KITCHEN");

                kitchen.setEnabled(true);

                userRepository.save(kitchen);
            }

        };
    }
}