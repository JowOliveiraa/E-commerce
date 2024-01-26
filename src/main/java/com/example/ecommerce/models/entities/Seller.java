package com.example.ecommerce.models.entities;

import com.example.ecommerce.models.dtos.SellerDTO;
import com.example.ecommerce.models.dtos.UpdateDTO;
import com.example.ecommerce.models.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "sellers")
public class Seller extends User{

    @Column(nullable = false)
    private Integer numberOfSales;

    public Seller(SellerDTO dto) {

        super(dto.name(), dto.cpf(), dto.email(), dto.password(), Role.SELLER);
        this.numberOfSales = 0;
    }

    public void update(UpdateDTO dto) {

        update(dto.name(), dto.email());
    }
}
