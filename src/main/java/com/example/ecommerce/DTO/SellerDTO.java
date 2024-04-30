package com.example.ecommerce.DTO;

import com.example.ecommerce.models.Product;
import lombok.Builder;

import java.util.List;

@Builder
public record SellerDTO(
        String name,
        String email,
        List<Product> products
) {
}
