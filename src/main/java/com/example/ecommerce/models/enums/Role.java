package com.example.ecommerce.models.enums;

public enum Role {
    ADMIN("admin"),
    SELLER("seller"),
    CUSTOMER("customer");

    private String role;

    Role(String role) {

        this.role = role;
    }
}
