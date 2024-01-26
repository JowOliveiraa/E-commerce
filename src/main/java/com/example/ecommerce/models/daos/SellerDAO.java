package com.example.ecommerce.models.daos;

import com.example.ecommerce.models.entities.Seller;
import com.example.ecommerce.models.enums.Role;
import com.example.ecommerce.models.enums.Status;

import java.time.LocalDateTime;

public record SellerDAO(
        Long id,
        String name,
        String cpf,
        String email,
        Integer numberOfSales,
        LocalDateTime registerAt,
        Role role,
        Status status
) {
    public SellerDAO(Seller seller) {

        this(
                seller.getId(),
                seller.getName(),
                seller.getCpf(),
                seller.getEmail(),
                seller.getNumberOfSales(),
                seller.getRegisterAt(),
                seller.getRole(),
                seller.getStatus()
        );
    }
}
