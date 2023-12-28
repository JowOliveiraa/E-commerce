package com.shop.models.dtos;

import com.shop.models.enums.Category;

import java.math.BigDecimal;

public record ProductDTO(
        String name,
        BigDecimal price,
        Integer quantity,
        Long sellerId,
        Category category) {
}
