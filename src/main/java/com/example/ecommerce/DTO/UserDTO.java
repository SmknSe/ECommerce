package com.example.ecommerce.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record UserDTO(
        UUID id,
        String name,
        String email,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        String password,
        String role,
        @JsonFormat(pattern = "dd-MM-yyyy hh:mm a z",
        timezone = "Europe/Moscow")
        LocalDateTime createdAt
) {
    public UserDTO(String name, String email,
                   String password, String role
    ) {
        this(null, name, email, password, role, LocalDateTime.now());
    }

    public UserDTO(String name, String email,
                   String password
    ) {
        this(null, name, email, password, "USER", LocalDateTime.now());
    }
}
