package com.shop.models.daos;

import com.shop.models.entities.Customer;
import com.shop.models.enums.Role;
import com.shop.models.enums.UserStatus;

import java.time.LocalDateTime;

public record CustomerDAO(

        Long id,
        String name,
        String cpf,
        String email,
        String cellPhone,
        Integer quantityOfPurchases,
        UserStatus status,
        Role role,
        LocalDateTime createdAt) {

    public CustomerDAO(Customer customer) {
        this(
                customer.getId(),
                customer.getName(),
                customer.getCpf(),
                customer.getEmail(),
                customer.getCellPhone(),
                customer.getQuantityOfPurchases(),
                customer.getStatus(),
                customer.getRole(),
                customer.getCreatedAt()
        );
    }
}
