package com.milsabores.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users") // 'user' es palabra reservada en SQL, mejor usar 'users'
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String name;

    private String role; // Aquí guardaremos "ADMIN" o "CUSTOMER"
}