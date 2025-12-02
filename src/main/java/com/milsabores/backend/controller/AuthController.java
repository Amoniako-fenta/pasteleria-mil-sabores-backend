package com.milsabores.backend.controller;

import com.milsabores.backend.dto.LoginRequest;
import com.milsabores.backend.dto.LoginResponse;
import com.milsabores.backend.dto.RegisterRequest; // <--- Nuevo import
import com.milsabores.backend.model.User;
import com.milsabores.backend.repository.UserRepository;
import com.milsabores.backend.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    // --- LOGIN (Ya lo tenías) ---
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(loginRequest.getPassword())) {
                String jwt = jwtUtils.generateJwtToken(user.getEmail(), user.getRole());
                return ResponseEntity.ok(new LoginResponse(
                        jwt, user.getId(), user.getEmail(), user.getRole(), user.getName()
                ));
            }
        }
        return ResponseEntity.status(401).body("Error: Credenciales incorrectas");
    }

    // --- REGISTRO (NUEVO) ---
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
        user.setRole("CUSTOMER"); // Por defecto, todos se registran como clientes

        // 3. Guardar en Base de Datos
        userRepository.save(user);

        return ResponseEntity.ok("¡Usuario registrado exitosamente!");
    }
}