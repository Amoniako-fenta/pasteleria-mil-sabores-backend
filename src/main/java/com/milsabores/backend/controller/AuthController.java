package com.milsabores.backend.controller;

import com.milsabores.backend.dto.LoginRequest;
import com.milsabores.backend.dto.LoginResponse;
import com.milsabores.backend.model.User;
import com.milsabores.backend.repository.UserRepository;
import com.milsabores.backend.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*") // Importante para React
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        
        // 1. Buscar el usuario por email
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // 2. Verificar contraseña (COMPARACIÓN SIMPLE TEXTO PLANO PARA LA EVALUACIÓN)
            // Nota: En un proyecto real usaríamos BCryptPasswordEncoder
            if (user.getPassword().equals(loginRequest.getPassword())) {
                
                // 3. Generar el Token JWT
                String jwt = jwtUtils.generateJwtToken(user.getEmail(), user.getRole());

                // 4. Devolver respuesta con Token y Datos
                return ResponseEntity.ok(new LoginResponse(
                        jwt,
                        user.getId(),
                        user.getEmail(),
                        user.getRole(),
                        user.getName()
                ));
            }
        }

        return ResponseEntity.status(401).body("Error: Credenciales incorrectas");
    }
}