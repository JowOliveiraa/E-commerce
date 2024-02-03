package com.example.ecommerce.models.daos;

import com.example.ecommerce.models.entities.User;
import com.example.ecommerce.models.enums.Role;
import com.example.ecommerce.models.enums.Status;

import java.time.LocalDateTime;
import java.util.Optional;

public record SellerDAO(
        Long id,
        String cpf,
        String name,
        String email,
        Integer numberOfSales,
        Role role,
        Status status,
        LocalDateTime registerAt
) {

    public SellerDAO(User user) {

        this(
                user.getId(),
                user.getCpf(),
                user.getName(),
                user.getEmail(),
                user.getNumberOfSales(),
                user.getRole(),
                user.getStatus(),
                user.getRegisterAt()
        );
    }
}
