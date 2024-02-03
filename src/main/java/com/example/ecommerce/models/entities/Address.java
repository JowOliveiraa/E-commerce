package com.example.ecommerce.models.entities;

import com.example.ecommerce.models.dtos.AddressDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, length = 50)
    private String street;

    @Column(nullable = false, length = 10)
    private String number;

    @Column(nullable = false, length = 10)
    private String zipCode;

    public Address(AddressDTO dto) {

        this.userId = dto.userId();
        this.street = dto.street();
        this.number = dto.number();
        this.zipCode = dto.zipCode();
    }
}
