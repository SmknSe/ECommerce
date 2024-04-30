package com.example.ecommerce.services;

import com.example.ecommerce.models.Order;
import com.example.ecommerce.persistence.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepo orderRepo;
    public void createOrder(Order order) {
        orderRepo.save(order);
    }
}
