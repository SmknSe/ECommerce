package com.example.ecommerce.persistence;

import com.example.ecommerce.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepo extends JpaRepository<Product, UUID> {
    public Optional<Product> findByTitle(String title);
}
