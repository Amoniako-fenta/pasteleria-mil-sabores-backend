package com.milsabores.backend.repository;
import com.milsabores.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // ¡No necesitas escribir código aquí! Spring ya sabe hacer el CRUD básico.
}