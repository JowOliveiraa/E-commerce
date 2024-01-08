package com.shop.models.daos;

import com.shop.models.entities.Costumer;
import com.shop.models.enums.Role;
import com.shop.models.enums.UserStatus;

import java.time.LocalDateTime;
import java.util.Date;

public record CostumerDAO(

        Long id,
        String name,
        String cpf,
        String email,
        String cellPhone,
        Integer quantityOfPurchases,
        UserStatus status,
        Role role,
        LocalDateTime createdAt) {

    public CostumerDAO(Costumer costumer) {
        this(
                costumer.getId(),
                costumer.getName(),
                costumer.getCpf(),
                costumer.getEmail(),
                costumer.getCellPhone(),
                costumer.getQuantityOfPurchases(),
                costumer.getStatus(),
                costumer.getRole(),
                costumer.getCreatedAt()
        );
    }
}
