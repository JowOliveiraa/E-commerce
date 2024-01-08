package com.shop.models.dtos;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public record SaleDTO(
        Long customerId,
        Long sellerId,
        Integer productsQuantity,
        Set<Long> productsId,
        BigDecimal price
) {
}
