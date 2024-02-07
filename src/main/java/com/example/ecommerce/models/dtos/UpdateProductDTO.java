package com.example.ecommerce.models.dtos;

import com.example.ecommerce.models.enums.Category;

import java.math.BigDecimal;

public record UpdateProductDTO(
        String name,
        BigDecimal price,
        Category category,
        Integer quantity
) {
}
