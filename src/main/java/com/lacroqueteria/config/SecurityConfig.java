package com.lacroqueteria.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()  // 🔹 Desactiva CSRF
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()  // 🔹 Permitir acceso a todos los endpoints
            )
            .headers().frameOptions().disable(); // 🔹 Permite usar H2 Console si lo necesitas

        return http.build();
    }
}
