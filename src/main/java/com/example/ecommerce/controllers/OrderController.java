package com.example.ecommerce.controllers;

import com.example.ecommerce.responses.BasicResponse;
import com.example.ecommerce.responses.DataResponse;
import com.example.ecommerce.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/cart")
    public ResponseEntity<? extends BasicResponse> getCart(Authentication authentication){
        return ResponseEntity.ok(orderService.getCart(authentication));
    }

    @DeleteMapping("/cart")
    public ResponseEntity<? extends BasicResponse> clearCart(Authentication authentication){
        return ResponseEntity.ok(orderService.clearCart(authentication));
    }

    @PostMapping("/cart/{uuid}")
    public ResponseEntity<? extends BasicResponse> addProduct(
            @PathVariable UUID uuid,
            Authentication authentication
            ){
        return ResponseEntity.ok(orderService.addProduct(uuid,authentication));
    }

    @DeleteMapping("/cart/{uuid}")
    public ResponseEntity<? extends BasicResponse> removeProduct(
            @PathVariable UUID uuid,
            Authentication authentication
    ){
        return ResponseEntity.ok(orderService.removeProduct(uuid,authentication));
    }

    @PostMapping("/cart/confirm")
    public ResponseEntity<? extends BasicResponse> confirmOrder(
            Authentication authentication
    ){
        return ResponseEntity.ok(orderService.confirmOrder(authentication));
    }

    @GetMapping
    public ResponseEntity<? extends BasicResponse> getOrderHistory(
            Authentication authentication
    ){
        return ResponseEntity.ok(orderService.getAllCurrentUserOrders(authentication));
    }
}
