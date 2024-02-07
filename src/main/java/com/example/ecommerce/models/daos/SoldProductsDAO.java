package com.example.ecommerce.models.daos;

import com.example.ecommerce.models.entities.SoldProducts;

public record SoldProductsDAO(
        Long sellerId,
        Long productId,
        Integer quantity
) {

    public SoldProductsDAO(SoldProducts soldProducts) {

        this(
                soldProducts.getSellerId(),
                soldProducts.getProductId(),
                soldProducts.getQuantity()
        );
    }
}
