package com.example.ecommerce.models.dtos;

import com.example.ecommerce.models.enums.Category;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record ProductDTO(
        @NotBlank
        Long sellerId,
        @NotBlank
        String name,
        @NotBlank
        BigDecimal price,
        @NotBlank
        Category category,
        @NotBlank
        Integer quantity

) {
}
