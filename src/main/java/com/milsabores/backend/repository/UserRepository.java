package com.milsabores.backend.repository;

import com.milsabores.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Método mágico: Spring crea la consulta SQL automáticamente por el nombre
    Optional<User> findByEmail(String email);
}