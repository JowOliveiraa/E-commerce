package com.example.ecommerce.models.dtos;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record AddressDTO(
        @NotBlank
        String streetAndNumber,
        @NotBlank
        @Length(max = 8, message = "o numero maximo de numeros é 8")
        String zipCode
) {
}
