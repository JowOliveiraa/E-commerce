package com.example.ecommerce.models.daos;

import com.example.ecommerce.models.entities.Customer;
import com.example.ecommerce.models.enums.Role;
import com.example.ecommerce.models.enums.Status;

import java.time.LocalDateTime;

public record CustomerDAO(
        Long id,
        String name,
        String cpf,
        String email,
        Integer numberOfPurchases,
        LocalDateTime registerAt,
        Role role,
        Status status
) {
    public CustomerDAO(Customer customer) {
        this(
                customer.getId(),
                customer.getName(),
                customer.getCpf(),
                customer.getEmail(),
                customer.getNumberOfPurchases(),
                customer.getRegisterAt(),
                customer.getRole(),
                customer.getStatus()
                );
    }
}
