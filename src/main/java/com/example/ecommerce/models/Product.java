package com.example.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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

    @ManyToOne
    @JsonIncludeProperties(value = "name")
    private Category category;

    @ManyToOne
    @JsonBackReference
    private Order order;

    @ManyToOne
    @JsonIncludeProperties(value = "name")
    private User owner;

    public Product(String title, BigDecimal price, String productImg) {
        this.title = title;
        this.price = price;
        this.productImg = productImg;
    }
}
