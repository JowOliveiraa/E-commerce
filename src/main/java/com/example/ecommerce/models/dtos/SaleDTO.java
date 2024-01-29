package com.example.ecommerce.models.dtos;

import com.example.ecommerce.models.orms.ProductsSold;

import java.util.List;

public record SaleDTO(
        Long customerId,
        Long sellerId,
        List<ProductsSold> products

) {
}
