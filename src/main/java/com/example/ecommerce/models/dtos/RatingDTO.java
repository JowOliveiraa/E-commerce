package com.example.ecommerce.models.dtos;

import jakarta.validation.constraints.NotBlank;

public record RatingDTO(
        @NotBlank
        Long customerId,
        @NotBlank
        Integer rating,
        @NotBlank
        String comment
) {
}
