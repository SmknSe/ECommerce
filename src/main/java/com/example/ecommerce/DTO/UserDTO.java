package com.example.ecommerce.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserDTO(
        UUID id,
        String firstname,
        String lastname,
        String username,
        String email,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        String password,
        String role,
        @JsonFormat(pattern = "dd-MM-yyyy hh:mm a z",
        timezone = "Europe/Moscow")
        LocalDateTime createdAt
) {
    public UserDTO(String firstname, String lastname, String username, String email,
                   String password, String role, LocalDateTime createdAt
    ) {
        this(null, firstname, lastname, username, email, password, role, createdAt);
    }

    public UserDTO(String firstname, String lastname, String username, String email,
                   String password
    ) {
        this(null, firstname, lastname, username, email, password, "USER", null);
    }
}
