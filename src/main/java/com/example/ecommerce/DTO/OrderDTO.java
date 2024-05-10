package com.example.ecommerce.DTO;

import com.example.ecommerce.enums.OrderStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record OrderDTO(
        UUID uuid,
        String status,
        String date,
        BigDecimal total
) {
}
