package com.example.ecommerce.enums;

public enum Roles {

    ADMIN("admin"),
    SELLER("vendedor"),
    CUSTOMER("cliente");

    private String role;

    Roles(String role) {
        this.role = role;
    }

}
