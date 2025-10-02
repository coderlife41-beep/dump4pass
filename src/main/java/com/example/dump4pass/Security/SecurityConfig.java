package com.example.dump4pass.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // allow both auth and otp APIs
                        .requestMatchers("/api/auth/**", "/api/otp/**").permitAll()
                        // everything else requires authentication
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
