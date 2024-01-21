package com.example.ecommerce.enums;

public enum Status {

    ACTIVE("ativo"),
    INACTIVE("inativo");

    private String status;
    Status(String status) {
        this.status = status;
    }
}
