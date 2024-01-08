package com.shop.models.daos;

import com.shop.models.entities.Product;
import com.shop.models.enums.Category;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductDAO(
        Long id,
        String name,
        BigDecimal price,
        Integer quantity,
        Integer numberOfSales,
        Long seller,
        Category category,
        LocalDateTime registeredAt
) {
    public ProductDAO(Product product) {
        this(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getQuantity(),
                product.getNumberOfSales(),
                product.getSellerId(),
                product.getCategory(),
                product.getRegisteredAt()
        );
    }
}
