package com.example.ecommerce.models.daos;

import com.example.ecommerce.models.entities.Sale;
import com.example.ecommerce.models.entities.SoldProducts;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record SaleDAO(
        Long id,
        Long customerId,
        Long sellerId,
        LocalDateTime registerAt,
        List<SoldProductsDAO> products
) {

    public SaleDAO(Sale sale) {

        this(
                sale.getId(),
                sale.getCustomerId(),
                sale.getSellerId(),
                sale.getRegisterAt(),
                sale.getProducts().stream().map(SoldProductsDAO::new).collect(Collectors.toList())
        );
    }
}
