package com.example.ecommerce.models.daos;

import com.example.ecommerce.models.entities.Rating;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RatingDAO(
        Long id,
        Long productId,
        Long customerId,
        Integer rating,
        String comment,
        LocalDateTime registerAt
) {

    public RatingDAO(Rating rating) {

        this(
                rating.getId(),
                rating.getProductId(),
                rating.getCustomerId(),
                rating.getRating(),
                rating.getComment(),
                rating.getRegisterAt()
        );
    }
}
