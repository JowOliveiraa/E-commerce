package com.example.ecommerce.models.daos;

import com.example.ecommerce.enums.Roles;
import com.example.ecommerce.models.entities.Customer;

import java.time.LocalDateTime;

public record CustomerDAO(
        Long id,
        String name,
        String cpf,
        String email,
        LocalDateTime registerAt,
        Integer numberOfPurchases,
        Roles role
) {
    public CustomerDAO(Customer customer) {
        this(
                customer.getId(),
                customer.getName(),
                customer.getCpf(),
                customer.getEmail(),
                customer.getRegisterAt(),
                customer.getNumberOfPurchases(),
                customer.getRole()
        );
    }
}
