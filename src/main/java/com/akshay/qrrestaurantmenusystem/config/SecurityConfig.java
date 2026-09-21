package com.akshay.qrrestaurantmenusystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {

    // ==========================================
    // PASSWORD ENCODER
    // ==========================================

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    // ==========================================
    // ROLE BASED LOGIN REDIRECT
    // ==========================================

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {

        return (request, response, authentication) -> {

            boolean isAdmin =
                    authentication.getAuthorities()
                            .stream()
                            .anyMatch(
                                    authority ->
                                    authority.getAuthority()
                                    .equals("ROLE_ADMIN")
                            );

            if (isAdmin) {

                response.sendRedirect("/dashboard");

                return;
            }

            boolean isKitchen =
                    authentication.getAuthorities()
                            .stream()
                            .anyMatch(
                                    authority ->
                                    authority.getAuthority()
                                    .equals("ROLE_KITCHEN")
                            );

            if (isKitchen) {

                response.sendRedirect("/kitchen/dashboard");

                return;
            }

            response.sendRedirect("/login?error");
        };
    }

    // ==========================================
    // SECURITY FILTER CHAIN
    // ==========================================

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http

            .authorizeHttpRequests(auth -> auth

                // ==============================
                // PUBLIC
                // ==============================

            		.requestMatchers(
            		        "/login",
            		        "/access-denied",
            		        "/css/**",
            		        "/js/**",
            		        "/images/**",
            		        "/uploads/**",
            		        "/customer/**"
            		).permitAll()

                // ==============================
                // ADMIN
                // ==============================

                .requestMatchers(
                        "/dashboard",
                        "/category/**",
                        "/menu/**",
                        "/table/**",
                        "/order/**"
                ).hasRole("ADMIN")

                // ==============================
                // KITCHEN
                // ==============================

                .requestMatchers(
                        "/kitchen/**"
                ).hasAnyRole("ADMIN", "KITCHEN")

                // ==============================
                // EVERYTHING ELSE
                // ==============================

                .anyRequest().authenticated()
            )

            // ==============================
            // LOGIN
            // ==============================

            .formLogin(form -> form

                .loginPage("/login")

                .successHandler(
                        authenticationSuccessHandler()
                )

                .permitAll()
            )
            
            .exceptionHandling(exception -> exception
                    .accessDeniedPage("/access-denied")
            )

            // ==============================
            // LOGOUT
            // ==============================

            .logout(logout -> logout

                .logoutUrl("/logout")

                .logoutSuccessUrl("/login?logout")

                .permitAll()
            );

        return http.build();
    }
}