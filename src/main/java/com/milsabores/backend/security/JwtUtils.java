package com.milsabores.backend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${milsabores.app.jwtSecret}")
    private String jwtSecret;

    @Value("${milsabores.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    // Generar el Token con el Email y el Rol
    public String generateJwtToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role) // Guardamos el rol dentro del token
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Obtener la llave de encriptación
    private Key key() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }
}