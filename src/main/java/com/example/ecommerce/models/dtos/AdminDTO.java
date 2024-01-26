package com.example.ecommerce.models.dtos;

import jakarta.validation.constraints.NotBlank;

public record AdminDTO(
        @NotBlank
        String name,
        @NotBlank
        String cpf,
        @NotBlank
        String email,
        @NotBlank
        String password
) {
}
