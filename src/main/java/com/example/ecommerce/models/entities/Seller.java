package com.example.ecommerce.models.entities;

import com.example.ecommerce.enums.Roles;
import com.example.ecommerce.models.dtos.SellerDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "sellers")
public class Seller extends User {

    @Column(nullable = false)
    private Integer numberOfSales;

    public Seller(SellerDTO dto) {
        super(dto.name(), dto.cpf(), dto.email(), dto.password(), Roles.SELLER);
        this.numberOfSales = 0;
    }

    public void doSale() {

        this.numberOfSales += 1;
    }
}


