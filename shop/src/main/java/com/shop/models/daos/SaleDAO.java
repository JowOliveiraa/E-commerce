package com.shop.models.daos;

import com.shop.models.orms.Sale;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public record SaleDAO(
        Long id,
        Long costumerId,
        Long sellerId,
        Integer productsQuantity,
        Set<Long> productsId,
        BigDecimal price,
        LocalDate date
) {
    public SaleDAO(Sale sale) {
        this(
                sale.getId(),
                sale.getCostumerId(),
                sale.getSellerId(),
                sale.getProductsQuantity(),
                sale.getProductsId(),
                sale.getPrice(),
                sale.getDate()
        );
    }
}
