package com.example.ecommerce.models.daos;

import com.example.ecommerce.models.entities.Admin;
import com.example.ecommerce.models.enums.Role;
import com.example.ecommerce.models.enums.Status;

import java.time.LocalDateTime;

public record AdminDAO(
        Long id,
        String name,
        String cpf,
        String email,
        LocalDateTime registerAt,
        Status status,
        Role role
) {
    public AdminDAO(Admin admin) {

        this(
                admin.getId(),
                admin.getName(),
                admin.getCpf(),
                admin.getEmail(),
                admin.getRegisterAt(),
                admin.getStatus(),
                admin.getRole()
        );
    }
}
