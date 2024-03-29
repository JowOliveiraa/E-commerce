package com.example.ecommerce.models.daos;

import com.example.ecommerce.models.entities.User;
import com.example.ecommerce.models.enums.Role;
import com.example.ecommerce.models.enums.Status;

import java.time.LocalDateTime;

public record CustomerDAO(
        Long id,
        String cpf,
        String name,
        String email,
        Integer numberOfPurchases,
        Role role,
        Status status,
        LocalDateTime registerAt
) {

    public CustomerDAO(User user) {

        this(
                user.getId(),
                user.getCpf(),
                user.getName(),
                user.getEmail(),
                user.getNumberOfPurchases(),
                user.getRole(),
                user.getStatus(),
                user.getRegisterAt()
        );
    }
}
