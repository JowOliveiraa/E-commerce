package com.example.ecommerce.models.dtos;

import jakarta.validation.constraints.NotBlank;

public record AddressDTO(
        @NotBlank
        Long userId,
        @NotBlank
        String street,
        @NotBlank
        String number,
        @NotBlank
        String zipCode
) {
}
