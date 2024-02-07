package com.example.ecommerce.models.dtos;

import com.example.ecommerce.models.entities.SoldProducts;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SaleDTO(
        @NotBlank
        Long customerId,
        @NotNull
        List<SoldProducts> products
) {
}
