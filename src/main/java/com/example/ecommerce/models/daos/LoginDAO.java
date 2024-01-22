package com.example.ecommerce.models.daos;

import com.example.ecommerce.enums.Roles;
import com.example.ecommerce.models.entities.Customer;

public record LoginDAO(
        Long userId,
        String name,
        Roles role,
        String token
) {
    public LoginDAO(Customer customer, String token) {
        this(
                customer.getId(),
                customer.getName(),
                customer.getRole(),
                token
        );
    }
}
