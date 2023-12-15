package com.shop.models.dtos;

import com.shop.models.enums.CostumerStatus;

public record CostumerDTO(
        String name,
        String cpf,
        String email,
        String cellPhone,
        CostumerStatus status) {
}
