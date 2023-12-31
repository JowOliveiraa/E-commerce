package com.shop.models.daos;

import com.shop.models.dtos.SellerDTO;
import com.shop.models.entities.Seller;
import com.shop.models.enums.Role;
import com.shop.models.enums.UserStatus;

import java.time.LocalDateTime;
import java.util.Optional;

public record SellerDAO(

        Long id,
        String name,
        String cpf,
        String email,
        String cellPhone,
        UserStatus status,
        Role role,
        Integer numberOfSales,
        LocalDateTime createdAt
) {
    public SellerDAO(Seller seller) {
        this(
                seller.getId(),
                seller.getName(),
                seller.getCpf(),
                seller.getEmail(),
                seller.getCellPhone(),
                seller.getStatus(),
                seller.getRole(),
                seller.getNumberOfSales(),
                seller.getCreatedAt()
        );
    }

}
