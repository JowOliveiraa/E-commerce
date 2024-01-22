package com.example.ecommerce.models.entities;

import com.example.ecommerce.enums.Roles;
import com.example.ecommerce.models.dtos.CustomerDTO;
import com.example.ecommerce.models.orms.Address;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "customers")
public class Customer extends User {

    @Column(nullable = false)
    private Integer numberOfPurchases;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Address address;

    public Customer(CustomerDTO dto) {
        super(dto.name(), dto.cpf(), dto.email(), dto.password(), Roles.CUSTOMER);
        this.numberOfPurchases = 0;
        this.address = new Address(dto.address());
    }

    public void doPurchase() {

        this.numberOfPurchases += 1;
    }
}
