package com.example.ecommerce.models.dtos;

import jakarta.validation.constraints.NotBlank;

public record RegisterDTO(
        @NotBlank
        String cpf,
        @NotBlank
        String name,
        @NotBlank
        String email,
        @NotBlank
        String password
) {
}
