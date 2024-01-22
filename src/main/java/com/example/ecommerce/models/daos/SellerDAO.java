package com.example.ecommerce.models.daos;

import com.example.ecommerce.enums.Roles;
import com.example.ecommerce.models.entities.Seller;

import java.time.LocalDateTime;

public record SellerDAO(
        Long id,
        String name,
        String cpf,
        String email,
        LocalDateTime registerAt,
        Integer numberOfSales,
        Roles role
) {
    public SellerDAO(Seller seller) {
        this(
                seller.getId(),
                seller.getName(),
                seller.getCpf(),
                seller.getEmail(),
                seller.getRegisterAt(),
                seller.getNumberOfSales(),
                seller.getRole()

        );
    }
}
