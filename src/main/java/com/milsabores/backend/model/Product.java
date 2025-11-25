package com.milsabores.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity // Esto le dice a Spring: "Crea una tabla llamada Product"
@Data   // Esto le dice a Lombok: "Crea getters y setters automáticos"
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID autoincremental
    private Long id;

    private String name;
    private String description;
    private Double price;
    private String imageUrl; // URL de la imagen del pastel
    private String category; // Ej: "Tortas", "Pasteles"
}