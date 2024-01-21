package com.example.ecommerce.models.dtos;

public record CustomerDTO(
        String name,
        String cpf,
        String email,
        String password
) {
}
