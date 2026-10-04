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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {

        return (request, response, authentication) -> {

            boolean isAdmin = false;
            boolean isKitchen = false;

            for (var authority : authentication.getAuthorities()) {

                if (authority.getAuthority().equals("ROLE_ADMIN")) {
                    isAdmin = true;
                }

                if (authority.getAuthority().equals("ROLE_KITCHEN")) {
                    isKitchen = true;
                }
            }


            if (isAdmin) {
                response.sendRedirect("/dashboard");
                return;
            }


            if (isKitchen) {
                response.sendRedirect("/kitchen/dashboard");
                return;
            }


            response.sendRedirect("/login?error");
        };
    }


    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
            .authorizeHttpRequests(auth -> auth

                .requestMatchers(
                    "/login",
                    "/access-denied",
                    "/css/**",
                    "/js/**",
                    "/images/**",
                    "/uploads/**",
                    "/customer/**",
                    "/cart/**"
                ).permitAll()


                .requestMatchers(
                    "/dashboard",
                    "/category/**",
                    "/menu/**",
                    "/table/**",
                    "/order/**",
                    "/admin/customer/**"
                ).hasRole("ADMIN")


                .requestMatchers(
                    "/kitchen/**"
                ).hasAnyRole("ADMIN", "KITCHEN")


                .anyRequest()
                .authenticated()
            )


            .formLogin(form -> form
                .loginPage("/login")
                .successHandler(authenticationSuccessHandler())
                .permitAll()
            )


            .exceptionHandling(exception -> exception
                .accessDeniedPage("/access-denied")
            )


            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );


        return http.build();
    }
}