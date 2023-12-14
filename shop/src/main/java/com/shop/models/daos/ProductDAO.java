package com.shop.models.daos;

import com.shop.models.entities.Product;
import com.shop.models.enums.Category;

import java.math.BigDecimal;

public record ProductDAO(
        Long id,
        String name,
        BigDecimal price,
        Integer quantity,
        Category category
) {
    public ProductDAO(Product product) {
        this(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getQuantity(),
                product.getCategory()
        );
    }
}
