package com.example.ecommerce.models.order;

import com.example.ecommerce.models.Product;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue
    private UUID id;

    private Integer quantity;

    private BigDecimal total;


    @ManyToOne
    private Product product;

    @ManyToOne
    private Order order;

    @PostUpdate
    @PostPersist
    private void postPersistOrUpdate(){
        recalculateTotal();;
    }

    @PostRemove
    private void postRemove(){
        if (order != null){
            order.recalculateTotal();
        }
    }

    public void recalculateTotal(){
        total = product.getPrice().multiply(BigDecimal.valueOf(quantity));
        if (order != null) {
            order.recalculateTotal();
        }
    }
}
