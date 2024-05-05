package com.example.ecommerce.persistence;

import com.example.ecommerce.models.Order;
import com.example.ecommerce.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepo extends JpaRepository<Order, UUID> {
    @Query("""
    SELECT o from Order o where o.user = :user and o.status='CART'
            """
    )
    Optional<Order> getCart(User user);

    List<Order> getOrderByUserId(UUID uuid);
}
