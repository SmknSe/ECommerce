package com.example.ecommerce.models;

import com.example.ecommerce.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private BigDecimal total;

    @OneToMany(mappedBy = "order")
    @JsonManagedReference
    private List<Product> products;

    @ManyToOne
    @JsonIgnore
    private User user;

    public void recalculateTotal(){
        total = products.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO,BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
