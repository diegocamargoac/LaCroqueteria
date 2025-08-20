package com.lacroqueteria.config;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Configuration
public class SecurityConfig {

	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
        	    .requestMatchers(new AntPathRequestMatcher("/login")).permitAll()
        	    .requestMatchers(new AntPathRequestMatcher("/user/login")).permitAll()
        	    //.requestMatchers(new AntPathRequestMatcher("/navegador")).permitAll()
        	    .requestMatchers(new AntPathRequestMatcher("/inventarioAdmin")).hasRole("ADMIN")
        	    //.requestMatchers(new AntPathRequestMatcher("/resumen")).permitAll()
        	    //.requestMatchers(new AntPathRequestMatcher("/user/logout")).permitAll()
        	    //.requestMatchers(new AntPathRequestMatcher("/user/**")).authenticated()
        	    //.requestMatchers(new AntPathRequestMatcher("/user/**")).hasRole("ADMIN")
        	    //.requestMatchers(new AntPathRequestMatcher("/images/logo")).permitAll()
        	    //.requestMatchers(new AntPathRequestMatcher("/user/**")).hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    
}