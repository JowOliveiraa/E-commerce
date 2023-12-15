package com.shop.models.daos;

import com.shop.models.entities.Costumer;
import com.shop.models.enums.CostumerStatus;

public record CostumerDAO(

        Long id,
        String name,
        String cpf,
        String email,
        String cellPhone,
        CostumerStatus status) {

    public CostumerDAO(Costumer costumer) {
        this(
                costumer.getId(),
                costumer.getName(),
                costumer.getCpf(),
                costumer.getEmail(),
                costumer.getCellPhone(),
                costumer.getStatus()
        );
    }
}
