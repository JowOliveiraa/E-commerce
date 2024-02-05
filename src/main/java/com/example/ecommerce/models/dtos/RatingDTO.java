package com.example.ecommerce.models.dtos;

import java.math.BigDecimal;

public record RatingDTO(
        Long productId,
        Long customerId,
        Integer rating,
        String comment
) {
}
