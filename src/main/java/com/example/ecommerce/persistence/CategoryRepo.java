package com.example.ecommerce.persistence;

import com.example.ecommerce.models.Category;
import com.example.ecommerce.responses.DataResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepo extends JpaRepository<Category, UUID> {
    Optional<Category> findByName(String name);

    @Query("select c.name from Category c")
    List<String> findAllNames();
}
