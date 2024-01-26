package com.example.ecommerce.models.orms;

import com.example.ecommerce.models.dtos.AddressDTO;
import com.example.ecommerce.models.entities.Customer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @OneToOne
    private Customer customer;

    @Column(nullable = false, length = 50)
    private String streetAndNumber;

    @Column(nullable = false, length = 8)
    private String zipCode;

    public Address(AddressDTO dto) {

        this.streetAndNumber = dto.streetAndNumber();
        this.zipCode = dto.zipCode();
    }

    public void update(AddressDTO dto) {

        this.streetAndNumber = dto.streetAndNumber();
        this.zipCode = dto.zipCode();
    }
}
