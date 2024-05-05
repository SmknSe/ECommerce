package com.example.ecommerce.models;

import com.example.ecommerce.enums.ProductStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue
    private UUID id;

    private String title;

    private BigDecimal price;

    private String productImg;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @ManyToOne
    @JsonIncludeProperties(value = "name")
    private Category category;

    @ManyToMany
    @JsonIgnore
    private Set<Order> orders;

    @ManyToOne
    @JsonIncludeProperties(value = {"id","name"})
    private User owner;

    public Product(String title, BigDecimal price, String productImg) {
        this.title = title;
        this.price = price;
        this.productImg = productImg;
    }
}
