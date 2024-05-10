package com.example.ecommerce.DTO;

import lombok.Builder;

@Builder
public record AuthDTO(
        UserDTO userDTO,
        String accessJwt,
        String refreshJwt
) {
}
