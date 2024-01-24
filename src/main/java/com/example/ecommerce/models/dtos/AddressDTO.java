package com.example.ecommerce.models.dtos;

import jakarta.validation.constraints.NotBlank;

public record AddressDTO(
        @NotBlank
        String streetAndNumber,
        @NotBlank
        String zipCode
) {
}
