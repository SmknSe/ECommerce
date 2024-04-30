package com.example.ecommerce.controllers;

import com.example.ecommerce.responses.BasicResponse;
import com.example.ecommerce.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<? extends BasicResponse> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<? extends BasicResponse> getUserInfo(
            @PathVariable UUID uuid
            ){
        return ResponseEntity.ok(userService.getUserById(uuid));
    }

    @GetMapping("/seller-info/{uuid}")
    public ResponseEntity<? extends BasicResponse> getSellerInfo(
            @PathVariable UUID uuid
    ){
        return ResponseEntity.ok(userService.getUserSellerInfoById(uuid));
    }
}
