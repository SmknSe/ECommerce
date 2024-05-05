package com.example.ecommerce.controllers;

import com.example.ecommerce.DTO.ProductDTO;
import com.example.ecommerce.responses.BasicResponse;
import com.example.ecommerce.services.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<? extends BasicResponse> createProduct(
            @RequestBody ProductDTO dto,
            Authentication authentication){
        var response = productService.createProduct(dto, authentication);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping
    public ResponseEntity<? extends BasicResponse> getAllProducts(){
        var response = productService.findAll();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<? extends BasicResponse> getProduct(
            @PathVariable UUID uuid
            ){
        var response = productService.findProductById(uuid);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PatchMapping("/{uuid}")
    public ResponseEntity<? extends BasicResponse> updateProduct(
            @RequestBody ProductDTO productDTO,
            @PathVariable UUID uuid,
            Authentication authentication
    ){
        var response = productService.updateProduct(uuid,productDTO,authentication);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<? extends BasicResponse> deleteProduct(
            @PathVariable UUID uuid
    ){
        return ResponseEntity.ok(productService.deleteProductById(uuid));
    }
}
