package com.example.ecommerce.DTO;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductDTO(
        String title,
        BigDecimal price,
        String category,
        String productImg
) {

}
