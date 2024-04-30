package com.example.ecommerce.DTO;

import java.math.BigDecimal;

public record ProductDTO(
        String title,
        BigDecimal price,
        String category,
        String productImg
) {

}
