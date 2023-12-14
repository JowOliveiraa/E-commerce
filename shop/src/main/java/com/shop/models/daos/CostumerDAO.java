package com.shop.models.daos;

import com.shop.models.entities.Costumer;

public record CostumerDAO(

        Long id,
        String name,
        String cpf,
        String email,
        String cellPhone) {

    public CostumerDAO(Costumer costumer) {
        this(
                costumer.getId(),
                costumer.getName(),
                costumer.getCpf(),
                costumer.getEmail(),
                costumer.getCellPhone()
        );
    }
}
