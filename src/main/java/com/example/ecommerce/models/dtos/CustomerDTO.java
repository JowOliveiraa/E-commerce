package com.example.ecommerce.models.dtos;

import com.example.ecommerce.models.orms.Address;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CustomerDTO(
        @NotBlank(message = "nome vazios")
        @Length(max = 50)
        String name,
        @NotBlank(message = "cpf vazio")
        @Length(max = 11)
        String cpf,
        @Length(max = 50)
        @NotBlank(message = "email vazio")
        String email,
        @NotBlank(message = "password vazio")
        String password,
        AddressDTO address
) {
}
