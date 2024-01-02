package com.shop.models.dtos;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public record SaleDTO(
        Long costumerId,
        Long sellerId,
        Integer productsQuantity,
        Set<Long> productsId,
        BigDecimal price
) {
}
