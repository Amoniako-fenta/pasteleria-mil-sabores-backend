package com.milsabores.backend.controller;

import com.milsabores.backend.dto.LoginRequest;
import com.milsabores.backend.dto.LoginResponse;
import com.milsabores.backend.dto.RegisterRequest;
import com.milsabores.backend.model.User;
import com.milsabores.backend.repository.UserRepository;
import com.milsabores.backend.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
// 👇 ESTA LÍNEA ES CRÍTICA: Permite que React (puerto 3000) hable con Java (puerto 8080) sin bloqueos
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    // --- LOGIN ---
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        // 1. Buscar usuario por email
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // 2. Verificar contraseña (texto plano para evaluación)
            if (user.getPassword().equals(loginRequest.getPassword())) {
                // 3. Generar Token
                String jwt = jwtUtils.generateJwtToken(user.getEmail(), user.getRole());
                
                // 4. Responder con Token y datos del usuario
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

    // --- REGISTRO ---
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest signUpRequest) {
        // 1. Verificar si el email ya existe
        if (userRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: El correo ya está en uso.");
        }

        // 2. Crear nuevo usuario
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        user.setRole("CUSTOMER"); // Por defecto es Cliente

        // 3. Guardar en Base de Datos
        userRepository.save(user);

        return ResponseEntity.ok("¡Usuario registrado exitosamente!");
    }
}