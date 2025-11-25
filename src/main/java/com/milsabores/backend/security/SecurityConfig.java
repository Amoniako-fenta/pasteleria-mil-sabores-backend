package com.milsabores.backend.security;

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
            // 1. Desactivar CSRF (necesario para H2 Console y Postman)
            .csrf(csrf -> csrf.disable())
            
            // 2. Configurar permisos de rutas
            .authorizeHttpRequests(auth -> auth
                // Permitir entrar a la consola de H2 sin login
                .requestMatchers("/h2-ui/**").permitAll()
                // Permitir entrar a Swagger sin login
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                // Permitir endpoints públicos (login, registro) - Lo usaremos luego
                .requestMatchers("/auth/**").permitAll()
                // Todo lo demás requiere autenticación (por ahora lo dejaremos abierto para probar)
                .anyRequest().permitAll() 
            )
            
            // 3. Permitir Frames (OBLIGATORIO para que H2 Console se vea)
            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));

        return http.build();
    }
}