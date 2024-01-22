package com.example.ecommerce.models.daos;

import com.example.ecommerce.enums.Roles;
import com.example.ecommerce.models.entities.Admin;

import java.time.LocalDateTime;

public record AdminDAO(
        Long id,
        String name,
        String cpf,
        String email,
        LocalDateTime registerAt,
        Roles role
) {
    public AdminDAO(Admin admin) {
        this(
                admin.getId(),
                admin.getName(),
                admin.getCpf(),
                admin.getEmail(),
                admin.getRegisterAt(),
                admin.getRole()
        );
    }
}
