package com.example.ecommerce.models.daos;

import com.example.ecommerce.models.entities.User;
import com.example.ecommerce.models.enums.Role;

import java.time.LocalDateTime;

public record RegisterDAO(
        Long id,
        String cpf,
        String name,
        String email,
        Role role,
        LocalDateTime registerAt
) {

    public RegisterDAO(User user) {

        this(
                user.getId(),
                user.getCpf(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getRegisterAt()
        );
    }
}
