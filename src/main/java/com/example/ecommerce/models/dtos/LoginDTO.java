package com.example.ecommerce.models.dtos;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
        @NotBlank
        String cpf,
        @NotBlank
        String password
) {
}
