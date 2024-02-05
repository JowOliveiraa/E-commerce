package com.example.ecommerce.models.daos;

import com.example.ecommerce.models.entities.Product;
import com.example.ecommerce.models.enums.Category;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductDAO(
        Long id,
        Long sellerId,
        String name,
        Integer quantity,
        BigDecimal price,
        Category category,
        BigDecimal rating,
        Integer numberOfRatings,
        LocalDateTime registerAt
) {

    public ProductDAO(Product product) {

        this(
                product.getId(),
                product.getSellerId(),
                product.getName(),
                product.getQuantity(),
                product.getPrice(),
                product.getCategory(),
                product.getRating(),
                product.getNumberOfRatings(),
                product.getRegisterAt()
        );
    }
}
