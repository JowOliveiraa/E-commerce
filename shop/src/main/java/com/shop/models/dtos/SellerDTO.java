package com.shop.models.dtos;

import com.shop.models.enums.UserStatus;

public record SellerDTO(

        String name,
        String cpf,
        String email,
        String cellPhone,
        UserStatus status,
        String password
) {
}
