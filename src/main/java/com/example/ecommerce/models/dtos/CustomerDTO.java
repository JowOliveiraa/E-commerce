package com.example.ecommerce.models.dtos;

import jakarta.validation.constraints.NotBlank;

public record CustomerDTO(
        @NotBlank
        String name,
        @NotBlank
        String cpf,
        @NotBlank
        String email,
        @NotBlank
        String password,
        @NotBlank
        AddressDTO address

) {
}
