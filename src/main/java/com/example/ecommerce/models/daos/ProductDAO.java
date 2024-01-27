package com.example.ecommerce.models.daos;

import com.example.ecommerce.models.entities.Product;
import com.example.ecommerce.models.enums.Category;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductDAO(
        Long id,
        String name,
        BigDecimal price,
        Category category,
        Integer quantity,
        float rating,
        Integer numberOfRantings,
        LocalDateTime registerAt,
        Long sellerId,
        Integer quantityOfSales

) {
    public  ProductDAO(Product product) {

        this(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getCategory(),
                product.getQuantity(),
                product.getRating(),
                product.getNumberOfRatings(),
                product.getRegisterAt(),
                product.getSellerId(),
                product.getQuantityOfSales()
        );
    }
}
