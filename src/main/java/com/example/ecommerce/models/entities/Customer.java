package com.example.ecommerce.models.entities;

import com.example.ecommerce.models.dtos.CustomerDTO;
import com.example.ecommerce.models.dtos.UpdateDTO;
import com.example.ecommerce.models.enums.Role;
import com.example.ecommerce.models.orms.Address;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "customers")
public class Customer extends User{

    @Column(nullable = false)
    private Integer numberOfPurchases;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    Address address;


    public Customer(CustomerDTO dto) {

        super(dto.name(), dto.cpf(), dto.email(), dto.password(), Role.CUSTOMER);
        this.numberOfPurchases = 0;
        this.address = new Address(dto.address());
        this.address.setCustomer(this);
    }

    public void update(UpdateDTO dto) {

        update(dto.name(), dto.email());
    }

    public void doPurchase() {

        this.numberOfPurchases += 1;
    }
}
