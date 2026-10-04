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
    public CommandLineRunner createUsers(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            // Admin user
            if (userRepository.findByUsername("admin").isEmpty()) {

                User admin = new User();

                admin.setUsername("admin");
                admin.setPassword(
                        passwordEncoder.encode("admin123"));
                admin.setRole("ADMIN");

                userRepository.save(admin);
            }


            // Kitchen user
            if (userRepository.findByUsername("kitchen").isEmpty()) {

                User kitchen = new User();

                kitchen.setUsername("kitchen");
                kitchen.setPassword(
                        passwordEncoder.encode("kitchen123"));
                kitchen.setRole("KITCHEN");

                userRepository.save(kitchen);
            }
        };
    }
}