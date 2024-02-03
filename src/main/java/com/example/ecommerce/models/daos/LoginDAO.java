package com.example.ecommerce.models.daos;

import com.example.ecommerce.models.entities.User;
import com.example.ecommerce.models.enums.Role;
import org.springframework.security.core.userdetails.UserDetails;

public record LoginDAO(
        Long id,
        String name,
        Role role,
        String token
) {

    public LoginDAO(User user, String token) {

        this(
                user.getId(),
                user.getName(),
                user.getRole(),
                token
        );
    }
}
