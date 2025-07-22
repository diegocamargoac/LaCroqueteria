package com.lacroqueteria.config;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {
	
	private String jwtSecret = "secret";  // Debes usar una clave secreta segura

    public String generateJwtToken(String username, String role) {
        return Jwts.builder()
            .setSubject(username)
            .claim("role", role)  // Añadimos el rol al token
            .signWith(SignatureAlgorithm.HS256, jwtSecret)
            .compact();
    }
	
}
