package com.milsabores.backend.controller;

import com.milsabores.backend.model.Product;
import com.milsabores.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products") // La URL base será http://localhost:8080/api/products
@CrossOrigin(origins = "*") // Permite que tu React se conecte sin errores
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // 1. OBTENER TODOS (GET)
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // 2. CREAR UNO (POST)
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    // 3. OBTENER POR ID (GET)
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productRepository.findById(id).orElse(null);
    }

    // 4. ELIMINAR (DELETE)
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
    }
    
    // 5. ACTUALIZAR (PUT) - Tarea para ti: ¿Te animas a implementarlo?
    // Si no, con estos 4 ya cumples gran parte.
}