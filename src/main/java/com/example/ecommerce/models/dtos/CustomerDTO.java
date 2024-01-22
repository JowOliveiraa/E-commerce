package com.example.ecommerce.models.dtos;

import com.example.ecommerce.models.orms.Address;
import jakarta.validation.constraints.NotBlank;

public record CustomerDTO(
        @NotBlank(message = "nome vazios")
        String name,
        @NotBlank(message = "cpf vazio")
        String cpf,
        @NotBlank(message = "email vazio")
        String email,
        @NotBlank(message = "password vazio")
        String password,
        AddressDTO address
) {
}
