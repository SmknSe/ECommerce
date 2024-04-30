package com.example.ecommerce.persistence;

import com.example.ecommerce.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepo extends JpaRepository<Order, UUID> {
}
